__author__ = 'abhididdigi'
from ezyapp.models import * #importing all models.
from django.apps import apps
from logger import ezyapp_logger
import importlib
import sys,os

# c_logger = ezyapp_logger()
def create_models(obj, mapping,relationship_array):
    one_row = obj
    model_list = []
    for dex,i in enumerate(mapping):

        single_obj = i
        kwargs = {}
        # c_logger.log(one_row,"warning")
        model,kwargs = type_based_clean_up(one_row,single_obj)

        # now finally instantiate the object.
        try:
            model_created = model(**kwargs)
            model_created.save();
            # c_logger.log("model being created","warning")
            ## c_logger.log("model created...","warning","I am a disco dancer");
            # # c_logger.log(model_created,"warning")
            model_list.append(model_created) # save the model.


        except Exception as e:
            # # c_logger.log("exception found","warning")
            # # c_logger.log(e,"warning")
            exc_type, exc_obj, exc_tb = sys.exc_info()
            fname = os.path.split(exc_tb.tb_frame.f_code.co_filename)[1]
            exception_details = str(exc_type) + str(fname) + str(exc_tb.tb_lineno)
            # c_logger.log(exception_details,"warning")
            # c_logger.log(str(e),"warning")

        # at this point all the models are created...
        # so create the relationships between them.
        # for all the models in the model_list list, start searching the relationship array to see if there are some
        # entries in the list.
        # # c_logger.log("printing the list of all models","warning")
        # # c_logger.log(model_list,"warning")
    try:
        for m,model in enumerate(model_list):
            current_model = model
            if m in relationship_array:
                relationships = relationship_array[m]
                # for each relationship, create the relationship.
                for key in relationships:
                    # get the model stored in the relationship corresponding to this model.
                    related_model = model_list[key]
                    # # c_logger.log("printing current model","warning")
                    # # c_logger.log(current_model,"warning")
                    # # c_logger.log("printing related model","warning")
                    # # c_logger.log(related_model,"warning")
                    # get the relationship.
                    mutual_relationship_column = relationships[key]
                    mutual_relationship_column_array = mutual_relationship_column.split('_')
                    if len(mutual_relationship_column_array) != 2:
                        raise Exception
                    else:
                        mutual_relationship = mutual_relationship_column_array[1]
                        mutual_relationship_column = mutual_relationship_column_array[0]

                    if mutual_relationship == "manytomany":
                        #pass
                        getattr(related_model,mutual_relationship_column).add(current_model)
                        return model_list
    except Exception as e:
        # c_logger.log("Error with adding relationships","warning")
        exc_type, exc_obj, exc_tb = sys.exc_info()
        fname = os.path.split(exc_tb.tb_frame.f_code.co_filename)[1]
        exception_details = str(exc_type) + str(fname) + str(exc_tb.tb_lineno)
        # c_logger.log(exception_details,"warning")




def type_based_clean_up(obj,mapping):
    kwargs = {};
    for key in mapping: #there is only one key that will be found.
        value = mapping[key]
        model = apps.get_model("ezyapp", model_name=key)
        for attr in value:
            attr_type = model._meta.get_field(attr).get_internal_type()
            attr_value = value[attr]
            if "()" in attr_value:
                attr_lookup_data = attr_value.replace("()",'')
            else:
                # c_logger.log(obj,"warning","warning from the else loop")
                attr_lookup_data = obj[attr_value]


            if attr_type == "IntegerField":
                if isinstance(attr_lookup_data,basestring):
                    if("." in attr_lookup_data): # looks to be a float type, so first convert it to float.
                        attr_lookup_data  = float(attr_lookup_data)
                    attr_lookup_data = int(attr_lookup_data)

            else:
                # # c_logger.log("inside the else that converts unicode strings into strin","warning")
                # # c_logger.log(type(attr_lookup_data),"warning")
                attr_lookup_data = str(attr_lookup_data)

            # # c_logger.log(attr,"warning")

            kwargs[attr] = attr_lookup_data
            # # c_logger.log(kwargs,"warning")

        return (model,kwargs)









def isfloat(x):
    try:
        a = float(x)
    except ValueError:
        return False
    else:
        return True

def isint(x):
    try:
        a = float(x)
        b = int(a)
    except ValueError:
        return False
    else:
        return a == b



# removed redundant code.

'''for  key in single_obj:

                kwargs = {}
                kwargs = type_based_clean_up()

                model = apps.get_model("ezyapp", model_name=key)
                model_names.append(key)

                attrs = single_obj[key]
                # # c_logger.log(attrs,"warning")
                kwargs = {}
                for attr in attrs:
                    # # c_logger.log(attr,"warning")
                    attr_value = attrs[attr]

                    # # c_logger.log(attr_value,"warning")
                    if '()' in attr_value: # hardcoded value.
                        #handles only int fields.
                        found_value = attr_value.replace("()",'')
                        # # c_logger.log(found_value, "warning");
                        if attr_type == "IntegerField":
                            # # c_logger.log(attr_type,"warning")
                            kwargs[attr] = int(found_value)
                            # # c_logger.log("abhiram is a disco dancer","warning")
                        else:
                            # # c_logger.log(attr_type,"warning")
                            # # c_logger.log(attr,"warning")
                            kwargs[attr] = found_value
                            # # c_logger.log("raghuram is a disco dancer","warning")
                    else:
                        lookup_value_found = one_row[attr_value]
                        if attr_type == "IntegerField":
                            if "number:" in lookup_value_found:
                                kwargs[attr] = int(float(lookup_value_found.replace("number:",'')))
                            else:
                                kwargs[attr] = int(lookup_value_found)
                        else:
                            if "number:" in lookup_value_found:
                                modified_lookup_value = lookup_value_found.replace("number:",'');
                            else:
                                modified_lookup_value = lookup_value_found;
                            kwargs[attr] = str(modified_lookup_value)
                # # c_logger.log("logging kwargs","warning")
                # # c_logger.log(kwargs,"warning")'''
