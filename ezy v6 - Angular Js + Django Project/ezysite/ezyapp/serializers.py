from rest_framework import serializers
from .models import Paper, SubSection, Option, Question, TestServer, Tests ,TestResponses ,TestScores
from django.contrib.auth import update_session_auth_hash
from ezyapp.models import Account
import logging


class TestScoresSerializer(serializers.ModelSerializer):

    class Meta:
        model = TestScores
        fields = '__all__'

class TestResponsesSerializer(serializers.ModelSerializer):

    class Meta:
        model = TestResponses
        fields = '__all__'

class TestsSerializer(serializers.ModelSerializer):

    class Meta:
        model = Tests
        fields = '__all__'

class QuestionSerializer(serializers.ModelSerializer):

    class Meta:
        model = Question
        exclude = ('answer_option','answer_text')
        depth = 1 #this is important since we did not define a nested serializer for options.

class SubSectionSerializer(serializers.ModelSerializer):
    questions = QuestionSerializer(many=True)

    class Meta:
        model = SubSection
        fields = '__all__'
        depth = 2 # commenting this line also gets the underlying models since nested serializers have been defined.

class PaperSerializer(serializers.ModelSerializer):

    subsections = SubSectionSerializer(many=True)

    class Meta:
      model  = Paper
      fields = ('id','label','instructions','paper_type','subsections')
      depth  = 3 # commenting this line also gets the underlying models since nested serializers have been defined.

class TesetServerSerializer(serializers.ModelSerializer):
    class Meta:
        model = TestServer
        fields = ('id','label','status','paper','classobj')
        depth = 1

class AccountSerializer(serializers.ModelSerializer):
    logging.warning('hello from serializer')
    password = serializers.CharField(write_only=True, required=False)
    confirm_password = serializers.CharField(write_only=True, required=False)
    email = serializers.EmailField(required=False)

    class Meta:
        model = Account
        fields = ('id', 'email', 'username', 'created_at', 'updated_at',
                   'password',
                  'confirm_password','first_name','last_name',)


        read_only_fields = ('created_at', 'updated_at',)

        def create(self, validated_data):
            logging.warning("inside the create function")
            return Account.objects.create(**validated_data)

        def update(self, instance, validated_data):

            #looks like we are updating only username. To update other fields we need to add them here.
            instance.username = validated_data.get('username', instance.username)

            instance.save()

            password = validated_data.get('password', None)
            confirm_password = validated_data.get('confirm_password', None)

            if password and confirm_password and password == confirm_password:
                instance.set_password(password)
                instance.save()

           #this will make sure that user does not have to login again. It will just update suthentication hash
            update_session_auth_hash(self.context.get('request'), instance)

            return instance