__author__ = 'abhididdigi'
from ezyapp.models import * #importing all models.
import json
from logger import ezyapp_logger
import xlrd
from django.apps import apps
from createmodels import create_models
import re # importing regular expressions.
from ezyapp.serializers import AccountSerializer
from ezyapp.models import Account


c_logger = ezyapp_logger();

class xl_import_parser:
    def __init__(self,wb,type,**kwargs):

        if type == "question_upload":


            self.workbook = wb
            self.sub_section_selection = kwargs.get("sub_section")
            self.paper_selection = kwargs.get("question")

            self.type= type # the type of the upload.

            self.sub_selected_options = json.loads(self.sub_section_selection)
            self.paper_selected_options = json.loads(self.paper_selection)

        elif type =="user_upload":
            self.workbook = wb
            self.type = type
            self.user_type = kwargs.get("user_type")


    def column_parse(self,excel): # helps to create the dictioanry for capturing choice[*] dict and also in validation.
        return_column_array = []
        row_idx = 0 # always check for the first column.
        col_nums = excel.ncols
        for i in range(0,col_nums):
            clm_obj = excel.cell(row_idx,i)
            return_column_array.append(str(clm_obj.value)) # push the column into a column.
        # c_logger.log(return_column_array,"warning"," :: is the array that the header is returning.")
        return return_column_array




    def parse(self,*args,**kwargs):
    
        try:
            if self.type == 'question_upload':
                # first get the paper and check if the paper already exists.
                # if the paper doesn't exist return an error to first create the paper through the service.


                # once paper is found, move on to subsection.
                # # c_logger.log(self.sub_section_selection,"warning")
                # # c_logger.log(type(self.sub_section_selection),"warning")
                retrieved_sub_section_label = self.sub_selected_options["id"];
                sSection = SubSection.objects.filter(id = retrieved_sub_section_label)

                #once a subsection is found, start parsing the questions and answers.

                if len(sSection) != 1:
                    raise ModelException(self.prepare_error(101))
                xl_sheet = self.workbook.sheet_by_index(0)
                header_row = self.column_parse(xl_sheet) # this is not handled in the excel parse as not all excel sheets needs validation.
                # # c_logger.log(row_dictionary,"warning")
                # generate the column mapping on the number of columns.
                # column mapping will already contain choices for question.

                # based on the header, prepare the options in column_to_model_mapping.
                # sample : {"Option":{"option_text":"choice_1","option_num":"()1"}}
                # validation gives us that, options start from `1`.

                column_mapping = {0:"question"}
                column_to_model_mapping = [{"Question":{"qtn_text":"question","qtn_type":"()-1","answer_option":"answer","answer_text":"()n.a"}}] # this should be dynamically generated to include options.
                for index,header_value in enumerate(header_row):
                    if "choice" in header_value.lower() or "answer" in header_value.lower():

                        column_mapping[int(index)] = header_value.lower()

                    if "choice" in header_value.lower():
                        choice_dict_one = {}
                        choice_dict_one["option_text"]  = header_value.lower()
                        choice_dict_one["option_num"]  = "()" + str(re.sub(r'choice',"",header_value,flags=re.IGNORECASE)) # line = re.sub(r"</?\[\d+>", "", line)
                        model_ = "Option"
                        choice_dict = {}
                        choice_dict[model_] = choice_dict_one
                        column_to_model_mapping.append(choice_dict)






                # c_logger.log(column_to_model_mapping,"warning"," :: is the log, for model mapping")
                # c_logger.log(column_mapping,"warning"," :: is the log, for just excel to column mapping")
                row_dictionary = self.read_excel_sheet(xl_sheet,column_mapping,"dict")
                c_logger.log(row_dictionary,"warning")











                column_to_relationship_mapping = {1:{0:"options_manytomany"}}
                for i in row_dictionary:
                    # c_logger.log(row_dictionary[i],"warning",": :calling the creation of model for dictionary")
                    model_list = create_models(row_dictionary[i],column_to_model_mapping,column_to_relationship_mapping)
                    # c_logger.log(model_list,"warning"," : : model list returned.")

                    # c_logger.log(sSection,"warning")
                    # now that all the questions and choices are complete, link them to the SubSection model.
                    if len(sSection) != 1:
                        raise ModelException(self.prepare_error("101"))
                    s = sSection[0] # getting the subsection.
                    # c_logger.log(model_list,"warning","Printing the model list, array")
                    s.questions.add(model_list[0]) # finally creating a relationship between question and subsection.
                    return "Question upload successful."
        except Exception as e:
            return "Error with Question upload"+str(e)

        try:
            # student upload.
            # setting password to default 1234
            # this will be called from a new page, that's called user upload that contains two options, student and instructor.
            if self.type == "user_upload":
                c_logger.log("inside the user upload...","warning")
                # mock data TODO before commit: replace this with actual data from the form.
                column_mapping = {0:"email",1:"first_name",2:"last_name",3:"username"}
                # read excel sheet...
                xl_sheet = self.workbook.sheet_by_index(0)
                all_rows = self.read_excel_sheet(xl_sheet,column_mapping,"list")
                c_logger.log("data returned..","warning")
                c_logger.log(all_rows,"warning")
                # we might call the serializer to get the job done for us.
                for us in all_rows:
                    us["password"] = "1234" # any hardcoded fields.
                    # finally create the users..
                    c_logger.log(us,"warning","getting created...")


                    serializer = AccountSerializer(data=us)
                    if serializer.is_valid():
                        c_logger.log(" The information we just passed is valid...","warning")
                        try:
                            Account.objects.create_user(**serializer.validated_data)
                        except Exception,e:
                            c_logger.log(str(e),"warning","exception created...")


                    user_model = None
                    c_logger.log(self.user_type,"warning"," is the user type passed...")
                    if "student" in self.user_type:
                        # get student's model.
                        user_model = Student
                    elif 'instructor' in self.user_type:
                        user_model = Instructor


                    # once the data is into the account table, get the model by username and attach it to a student or master.
                    account = Account.objects.filter(username=us["username"])
                    account1 = account[0] #getting one and only user..
                    c_logger.log(account,"warning","Account being created . . . ")

                    corresponding_user = user_model(user = account1)
                    corresponding_user.save()
                    return {"message":"user upload successful.","status":1}


        except Exception as e:
            error =  "Error with user upload and the error is : " + str(e)
            return_dict = {}
            return_dict["error"]  = error;
            return_dict["status"]  = -1;
            return return_dict





    def read_excel_sheet(self,sheet,sheet_mapping,type):

        row_dictionary = {};
        xl_sheet  = sheet
        column_mapping = sheet_mapping
        num_cols = xl_sheet.ncols   # Number of columns
        if type == "dict": # if you want the result as a dict :
            row_dictionary = {}
        elif type == "list":
            row_dictionary = []

        for row_idx in range(1, xl_sheet.nrows):    # Iterate through rows
            # # c_logger.log(row_idx,"warning")   # Print row number
            # mapping ...

            column_dictionary = {};

            for col_idx in range(0, num_cols):  # Iterate through columns

                cell_obj = xl_sheet.cell(row_idx, col_idx)  # Get cell object by row, col

                # # c_logger.log(col_idx,"warning")
                # # c_logger.log(cell_obj.value,"warning")
                c_logger.log(column_mapping,"warning","::: is the column mapping being used...");
                column_dictionary[column_mapping[col_idx]] = cell_obj.value
            if type == "dict": # if you want the result as a dict :
                row_dictionary[row_idx] = column_dictionary
            elif type == "list":
                row_dictionary.append(column_dictionary)
        return row_dictionary

    def prepare_error(self,error_message):

        ERROR_CONSTANTS =  {};
        ERROR_CONSTANTS["100"] = "The paper you are looking for is not found (or) more than one paper found. Please first create a paper and try again"
        ERROR_CONSTANTS["101"] = "The subsection you are looking for is not found (or) more than one subsection found. Please first create a subsection / check the upload file and try again"


        return {"error":ERROR_CONSTANTS[error_message],"status":"-1"}


class ModelException(Exception):
        pass