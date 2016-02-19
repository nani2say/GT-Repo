from django.db import models
from django.contrib.auth.models import AbstractBaseUser
from django.contrib.auth.models import BaseUserManager
from django.contrib.auth.models import PermissionsMixin
import logging
import pdb

# Create your models here.

class Option(models.Model):
      option_text  = models.CharField(max_length=400)
      option_num   = models.IntegerField()
      # add field to hold image or a image url in future

      def __unicode__(self):
            return self.option_text

class Question(models.Model):
      qtn_text = models.TextField()
      qtn_type = models.IntegerField()
      answer_option = models.IntegerField() #option 1, 2 , 3 or 4
      answer_text = models.CharField(max_length=200) #used to validate fill in the blanks # this curren
      options = models.ManyToManyField(Option)
      # add user_id field later - to indicate who inserted the field

      def __unicode__(self):
          return self.qtn_text

class SubSection(models.Model):
     label = models.CharField(max_length=200)
     description = models.TextField()
     subsection_type = models.IntegerField()
     timer = models.TimeField() #need to check if this can act as real timer. or just as time of record entry
     questions = models.ManyToManyField(Question)
     def __unicode__(self):
          return self.label


class AccountManager(BaseUserManager):

    def create_user(self,email, password=None,**kwargs):

       # pdb.set_trace()

        #if not email:
            #raise ValueError('Users must have a valid email address.')

        if not kwargs.get('username'):
            raise ValueError('Users must have a valid username.')

       # email = kwargs.get("email");
        #password  = kwargs.get("password")
        logging.warning(email)
        logging.warning(password)

        if not password:
            password = None
        account = self.model(
            email=email, username=kwargs.get('username'),first_name=kwargs.get("first_name"),
            last_name = kwargs.get("last_name")
        )


        account.set_password(password)
        account.save()

        return account

    def create_superuser(self, email, password, **kwargs):
        account = self.create_user(email, password, **kwargs)

        account.is_admin = True
        account.is_superuser = True
        account.is_staff = True
        account.save()

        return account


class Account(AbstractBaseUser, PermissionsMixin):

    email = models.EmailField(unique=True)
    username = models.CharField(max_length=100, unique=True)

    first_name = models.CharField(max_length=100, blank=False)
    last_name = models.CharField(max_length=100, blank=False)

    is_admin = models.BooleanField(default=False)
   # is_superuser = models.BooleanField(default=False)
    is_active = models.BooleanField(default=True)
    is_staff = models.BooleanField(default=False)

    created_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now=True)


    objects = AccountManager()

    USERNAME_FIELD = 'username'
    REQUIRED_FIELDS = ['email','first_name','last_name'] #this is creating problems

    def __unicode__(self):
        return self.username

 #the below methods may not be used at all but i can use them as examples to define other methods in other models

    def get_full_name(self):
        return ' '.join([self.first_name, self.last_name])

    def get_short_name(self):
        return self.first_name




class Instructor(models.Model):
    user = models.OneToOneField(Account)

    class Meta:
        db_table = 'ezapp_instructor'

    def __unicode__(self):
          return self.user.username


class Student(models.Model):
    user = models.OneToOneField(Account)

    class Meta:
        db_table = 'ezapp_student'

    def __unicode__(self):
          return self.user.username


class Classes(models.Model):
    label = models.CharField(max_length=200)
    created_at = models.DateTimeField(auto_now_add=True)
    students = models.ManyToManyField(Student)

    def __unicode__(self):
          return self.label

class Paper(models.Model):
     label = models.CharField(max_length=200)
     instructions = models.TextField()
     paper_type  = models.IntegerField()
     subsections = models.ManyToManyField(SubSection)
     owner = models.ForeignKey(Instructor)
     def __unicode__(self):
          return self.label

#we may need to add "created_by" (Instructor) field.
class TestServer(models.Model):
    label = models.CharField(max_length=200)
    status = models.IntegerField() #status could be ON or OFF. ON means 1 and OFF means 0.
    created_at = models.DateTimeField(auto_now_add=True)
    paper = models.ForeignKey(Paper)  #remove id from here i.e make it just paper
    classobj = models.ForeignKey(Classes) #remove id from here i.e make it just class

    def __unicode__(self):
          return self.label

class Tests(models.Model):
    tserver = models.ForeignKey(TestServer)
    student = models.ForeignKey(Student)  #it was class_id before. need to update or recreate database
    start_time = models.DateTimeField()
    end_time = models.DateTimeField(null=True)
    status = models.IntegerField() #status could 0,1 or 2. Zero means test havent started yet. 1 means test in progress. 2 means test was submitted.
    def __unicode__(self):
          return str(self.id)

#here testid + subsec + question will uniquely identify a row.( A question may belong many subsections.)
class TestResponses(models.Model):
    test         = models.ForeignKey(Tests)
    subsec       = models.ForeignKey(SubSection)
    question     = models.ForeignKey(Question)
    option_num   = models.IntegerField()
    correct      = models.BooleanField()

class TestScores(models.Model):
    test   =  models.ForeignKey(Tests)  #use test_id to store
    subsec =  models.ForeignKey(SubSection) #use subsec_id to store
    score  =  models.IntegerField()