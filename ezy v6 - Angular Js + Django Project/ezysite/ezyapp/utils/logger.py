__author__ = 'abhididdigi'
# a set of functions that can help us in logging the information.

import logging

# TODO : v2 improve logger.

class ezyapp_logger:

    def __init__(self):
        self.should_log = True;

    def log(self,message,level,*args):
        if isinstance(message,basestring):
            pass
        else:
            message = str(message)

        logged_string = ' '.join(args) + " :: " + message

        if self.should_log == False:
            return;
        if level == "warning":
            logging.warning(logged_string);
            


