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


# basic validator

class xl_import_validator:

    def __init__(self,xl,type):
        self.workbook = xl
        self.type = type;
        self.error = None




    def column_parse(self,excel): # helps to create the dictioanry for capturing choice[*] dict and also in validation.
        return_column_array = []
        row_idx = 0 # always check for the first column.
        col_nums = excel.ncols
        for i in range(0,col_nums):
            clm_obj = excel.cell(row_idx,i)
            return_column_array.append(str(clm_obj.value)) # push the column into a column.
        # c_logger.log(return_column_array,"warning"," :: is the array that the header is returning.")
        return return_column_array

    def validate(self):
        # get the first column.
        try:
            xl_sheet = self.workbook.sheet_by_index(0)
            header_row = self.column_parse(xl_sheet) # this is not handled in the excel parse as not all excel sheets needs validation.

            # this is a lite parser. We only check for the sequence of columns and see if they all are in format.
            # for question upload, we check for Question, Choice * , Answer.

            last_index = int(len(header_row)) - 1

            if self.type == 'question_upload':

                for index,v in enumerate(header_row):
                    if index == 0:
                        if "question" in v.lower():
                            pass
                        else:
                            self.error = self.prepare_error(101)
                            raise ExcelParseException()
                    elif index == last_index:
                        if "answer"  in v.lower():
                            pass
                        else:
                            self.error = self.prepare_error(102)
                            raise ExcelParseException
                    else: # for any other cases
                        if "choice" in v.lower():
                            pass
                        else:
                            self.error = self.prepare_error(103)
                            raise ExcelParseException()

            elif self.type == 'user_upload':

                for index,v in enumerate(header_row):
                    if index == 0:
                        if "email" in v.lower():
                            pass
                        else:
                            self.error = self.prepare_error(203)
                            raise ExcelParseException()
                    if index == 1:
                        if "first name" in v.lower():
                            pass;
                        else:
                            self.error = self.prepare_error(202)
                            raise ExcelParseException()
                    if index == 2:
                        if "last name" in v.lower():
                            pass
                        else:
                            self.error = self.prepare_error(203)
                            raise ExcelParseException()
                    if index == 3:
                        if "username" in v.lower():
                            pass
                        else:
                            self.error = self.prepare_error(204)
                            raise ExcelParseException

            return {"status":"1","message":"validated_successfully."}

        except ExcelParseException as e:
            return {"status":-1, "error":self.error}




    def prepare_error(self,num):
        if num == 101:
            return "The first column of the upload should be `Question`. Please correct and re-upload."
        if num == 102:
            return " The last column of the upload should be `Answer`. Please correct and re-upload."
        if num == 103:
            return "The upload should only consist of columns Question, Answer, Choice. Found different columns."



class ExcelParseException(Exception):
    pass

