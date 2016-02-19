from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from django.http import HttpResponse
from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser
from .models import Paper, SubSection, Option, Question, TestServer, Classes, Tests, Student, Instructor
from .serializers import PaperSerializer , TesetServerSerializer,TestsSerializer ,TestResponsesSerializer, TestScoresSerializer
from rest_framework import permissions, viewsets,generics
from ezyapp.models import Account
from ezyapp.permissions import IsAccountOwner
from ezyapp.serializers import AccountSerializer
from django.views.decorators.csrf import ensure_csrf_cookie
from rest_framework.decorators import api_view
from django.views.generic.base import TemplateView
from django.utils.decorators import method_decorator
import logging
import json
from django.contrib.auth import authenticate, login
from rest_framework import status, views
from rest_framework.response import Response
from django.http import JsonResponse
from rest_framework.parsers import FormParser,MultiPartParser
from utils.xlimportparser import xl_import_parser
from utils.xlimportvalidator import  xl_import_validator
from django.contrib.auth import logout
from utils.logger import ezyapp_logger # a basic logging functionality. Check code samples for examples.
import xlrd
from django.http import Http404
from django.core.exceptions import ObjectDoesNotExist
from django.views.defaults import page_not_found
from datetime import datetime

# Create your views here.

# commenting this code out as we can use an OOB JsonResponse object to basically send back a JSON.
'''
class JSONResponse(HttpResponse):
    """
    An HttpResponse that renders its content into JSON.
    """
    def __init__(self, data, **kwargs):
        content = JSONRenderer().render(data)
        kwargs['content_type'] = 'application/json'
        super(JSONResponse, self).__init__(content, **kwargs)
'''


c_logger = ezyapp_logger()

# @csrf_exempt # this is regarding permissions i think
# we are not using this View currently
def paper_list(request):

    if request.method == 'GET':
        paper = Paper.objects.all()
        serializer = PaperSerializer(paper, many=True)
        return JsonResponse(serializer.data,safe=False) #why safe= false?



# currently this view is not returning any success or failure messages back. Need relevant mesgs like when the email already exists.
class AccountViewSet(viewsets.ModelViewSet):
    lookup_field = 'username'
    queryset = Account.objects.all()
    serializer_class = AccountSerializer

    def get_permissions(self):
       # logging.warning('Watch out! from perm')

        if self.request.method in permissions.SAFE_METHODS: #Safe methods include GET etc but Not Delete() and update()
            return (permissions.AllowAny(),)

       # logging.warning('after safe methods23')

        if self.request.method == 'POST': # we should also allow new users for registration apart from SAFE METHODS
            return (permissions.AllowAny(),)

       # logging.warning('after post allow any')

        #For profile changes and delete(maybe ) we verify if 1.the suer is authenticated and 2.he is the owner
        #IsAccountOwner is defined in permissions.py file (within the app not inbuilt)
        return (permissions.IsAuthenticated(), IsAccountOwner(),)


    def create(self, request):

     #   logging.warning('data22344')

        serializer = self.serializer_class(data=request.data)

        #logging.warning('after serializer12 ')
     #   c_logger.log(request.data,"warning","the request that is being fetched::")
        #pdb.set_trace()
        #logging.warning(serializer.is_valid())
       #create_user (inbuilt method) will take care of the password hashing etc.
        if serializer.is_valid():
            logging.warning(serializer.validated_data)
            try:
                Account.objects.create_user(**serializer.validated_data)
            except Exception,e:
                logging.warning(str(e))
           #need to resulve the Response variable and user a proper template or Json may be
            return Response(serializer.validated_data, status=status.HTTP_201_CREATED)
           # return JSONResponse(serializer.validated_data)  #This line is atleast not giving a 500 error on server . although on success

        logging.warning('after is_valid ')

       # return JSONResponse(serializer.validated_data) #try this later. for template error browser directly
        return Response({
            'status': 'Bad request',
            'message': 'Account could not be created with received data.'
        }, status=status.HTTP_400_BAD_REQUEST)



class IndexView(TemplateView):
    template_name = 'index.html'

    @method_decorator(ensure_csrf_cookie)
    def dispatch(self, *args, **kwargs):
        return super(IndexView, self).dispatch(*args, **kwargs)


class LoginView(views.APIView):
    logging.warning("calling the login view")
    def post(self, request, format=None):
        data = request.data #json.loads(request.body) getting this eror  - raise RawPostDataException("You cannot access body after reading from request's data stream"

        user_name = data.get('username', None)
        password = data.get('password', None)
        logging.warning(user_name)
        logging.warning(password)
        account = authenticate(username=user_name, password=password)  #have to take cate of email/username
        logging.warning(account);
        if account is not None:
            logging.warning(account.is_active)
            if account.is_active:
                login(request, account)

                serialized = AccountSerializer(account)
                logging.warning(serialized.data)
                return Response(serialized.data)
            else:
                return Response({
                    'status': 'Unauthorized',
                    'message': 'This account has been disabled.'
                }, status=status.HTTP_401_UNAUTHORIZED)
        else:
            return Response({
                'status': 'Unauthorized',
                'message': 'Username/password combination invalid.'
            }, status=status.HTTP_401_UNAUTHORIZED)


class LogoutView(views.APIView):
    permission_classes = (permissions.IsAuthenticated,)

    def post(self, request, format=None):
        logout(request)

        return Response({}, status=status.HTTP_204_NO_CONTENT)


class TestServersView(views.APIView):

    def get(self, request, format=None):
        current_user = request.user
        #print current_user.id
        student = current_user.student #way to get one-one fields. #need to take care of RelatedObjectDoesNotExist exception. i.e a check if the person is a student??
        classes = student.classes_set.all() #get all classes for the student. we get only one class most of the time.
                                            # this is the notation to acces manytomany field related objects from slave class(Student).
        #for all classes get all test servers
        test_servers = TestServer.objects.filter(classobj_id__in=classes) #select all testserver objects where classes IN {list of classes)
       # print test_servers

        serializer = TesetServerSerializer(test_servers, many=True)
        return JsonResponse(serializer.data,safe=False) #why safe= false?

class PaperView(views.APIView):

     def get(self, request, pk, format=None):

       try:
          paper = Paper.objects.get(pk=pk)
          paper = PaperSerializer(paper)
          #print paper.data
          return JsonResponse(paper.data,safe=False)

       except ObjectDoesNotExist:
          raise Http404  #this raises an exception currently need to fix it.
          #return page_not_found(self,request)

# I am not going to use a serializer here
#This view should be used for GET and POST which dont require primary key
class TestStartListView(views.APIView):
    logging.warning("calling the test start list view")

    #did not handle failed case here. If the record is created amd exception is raised.
    # We might have to re-write this method in future to accomodate complete data object being passed in
    def post(self, request, format=None):
        data = request.data
        tserverid = data.get('tserver_id', None)

        #start-time -> its useing server computers local time. Its not timezone aware. To make it timezoen aware set USE_TZ = False in settings.py
        testobj = Tests(tserver_id = tserverid , student_id = request.user.student.id , start_time = datetime.now() ,status=1)
        testobj.save()
        serializer = TestsSerializer(testobj)
        return Response(serializer.data, status=status.HTTP_201_CREATED)


#This view should be used for PUT and DELETE which needs a primary key
#here our request.DATA does not have any data in it. So i skipped the data = request.data argument
# We might have to re-write this method in future to accomodate complete data object being passed in
class TestStartUpdateView(views.APIView):

    def put(self, request, pk, format=None):

         try:
           testobj = Tests.objects.get(pk=pk)
         except Tests.DoesNotExist:
           return Response(status=status.HTTP_404_NOT_FOUND)

         #update end_time
         testobj.end_time = datetime.now()
         testobj.save()
         serializer = TestsSerializer(testobj)
         return Response(serializer.data)

#need to take care of validations errors or null passed in or if save fails on serializers
class TestResponsesView(views.APIView):

       def post(self, request, format=None):

        scoredata = {}
        score = 0;

        for item in request.data:
            # print item     #print item['option_num']
             test   = item['test']
             option = item['option_num']
             qtn    = item['question']
             subsec = item['subsec']
             item['correct'] = False

             #get the subsection object
             correctans = SubSection.objects.get(pk = subsec).questions.all().get(pk = qtn).answer_option
             if(correctans == option):
                 score = score + 1
                 item['correct'] = True

        #post the score to TestScores model.
        #print "score=" +str(score)

        scoredata['test']   = test
        scoredata['subsec'] = subsec
        scoredata['score']  = score

        #print scoredata

        #save the responses entries
        serializer = TestResponsesSerializer(data=request.data, many=True)
        if serializer.is_valid():
            serializer.save()

        #save the score entry for this subsection
        scoreserializer = TestScoresSerializer(data = scoredata)
        if scoreserializer.is_valid():
            scoreserializer.save()
            return Response(scoreserializer.data, status=status.HTTP_201_CREATED)

        return Response(scoreserializer.errors, status=status.HTTP_400_BAD_REQUEST)

#pass in the subsection Id and testId .

'''
class TestScoreView(views.APIView):

       def get(self, request, format=None):
       #need to write logic to get test scores
'''

class FileUploadView(views.APIView):
    logging.warning("inside file upload view") ;

    parser_classes = (FormParser, MultiPartParser, )


    def post(self, request, format=None):
        file_obj = request.data['file']
        post_data = request.data["selected_sub_section"];
        question_data = request.data["selected_paper"];
        logging.warning(post_data)
        logging.warning(file_obj)
        input_excel = request.FILES['file']
        input_excel_2 = input_excel
        # call the validator here. and verify if everything is good.
        workbook = xlrd.open_workbook(file_contents=input_excel.read())
        validator_string = xl_import_validator(workbook,"question_upload").validate()
        if validator_string["status"]  == -1:
            return Response(validator_string,status=status.HTTP_200_OK)


        return_string = xl_import_parser(workbook,"question_upload",question=question_data,sub_section=post_data).parse();
        # ...
        # do some stuff with uploaded file
        # ...
        logging.warning("inside  the put method")
        logging.warning(return_string)
        return Response({'data':return_string},status=status.HTTP_200_OK)


class FileUserUploadView(views.APIView):

    logging.warning("inside file upload view") ;

    parser_classes = (FormParser, MultiPartParser, )


    def post(self, request, format=None):
        file_obj = request.data['file']
        user_selected = request.data["selected_user"];


        logging.warning(user_selected)
        logging.warning(file_obj)
        input_excel = request.FILES['file']

        return_string = xl_import_parser(input_excel,"user_upload",user_type = user_selected).parse();
        # ...
        # do some stuff with uploaded file
        # ...

        return Response(return_string,status=status.HTTP_200_OK)



def file_upload(request):
    return HttpResponse("file uploaded successfully" + request.data[file])