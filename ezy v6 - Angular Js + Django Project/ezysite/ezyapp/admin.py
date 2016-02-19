from django.contrib import admin
from django.contrib.auth import get_user_model
from .models import Paper, SubSection, Option, Question, Instructor, Student, Classes, TestServer, Tests

# Register your models here.

User = get_user_model()

admin.site.register(User)
admin.site.register(Paper)
admin.site.register(SubSection)
admin.site.register(Option)
admin.site.register(Question)
admin.site.register(Instructor)
admin.site.register(Student)
admin.site.register(Classes)
admin.site.register(TestServer)
admin.site.register(Tests)