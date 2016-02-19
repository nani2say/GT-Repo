"""ezysite URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.8/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Add an import:  from blog import urls as blog_urls
    2. Add a URL to urlpatterns:  url(r'^blog/', include(blog_urls))
"""
from django.conf.urls import include, url,patterns
from rest_framework import routers
from django.contrib import admin
from views import hello
from ezyapp.views import paper_list , AccountViewSet , IndexView, file_upload  #This line may be shown with warning in Pycharm. you can ignore it.
from ezyapp.views import LoginView, LogoutView, FileUploadView, FileUserUploadView, TestServersView, PaperView, TestStartListView ,\
     TestStartUpdateView ,TestResponsesView



router = routers.SimpleRouter()
router.register(r'accounts', AccountViewSet)

urlpatterns = [

    url(r'^admin/', include(admin.site.urls)),
    url(r'^hello/$', hello),
    url(r'^paper/$', paper_list),


    #these are urls related to account
    url(r'^api/v1/', include(router.urls)), #remember to call with a forward slash at the end. esp when calling directly
    url(r'^api/v1/auth/login/$', LoginView.as_view(), name='login'),
    url(r'^api/v1/auth/logout/$', LogoutView.as_view(), name='logout'),

    # file upload view
    url(r'^api/v1/fileUpload/',FileUploadView().as_view(),name='fileUpload'), #fileUserUpload
    url(r'^api/v1/fileUserUpload/',FileUserUploadView().as_view(),name='fileUpload'),
    # file_upload

    #url(r'^api/v1/fileUpload/',file_upload),
    url(r'^api/v1/testservers/$', TestServersView.as_view(), name='testservers'),
    url(r'^api/v1/getpaper/(?P<pk>[0-9]+)/$', PaperView.as_view()),

    #This view should be used for GET and POST which dont require primary key
    url(r'^api/v1/testrecord/$', TestStartListView.as_view(), name='starttest'),
    #This view should be used for PUT and DELETE which needs a primary key
    url(r'^api/v1/testrecord/(?P<pk>[0-9]+)/$', TestStartUpdateView.as_view()),

    #view to Store multiple test responses at once
    url(r'^api/v1/testresponse/$', TestResponsesView.as_view(), name='testresponse'),

    # I think this should point to the home page.
    # This is known as a passthrough or catch-all route. It accepts all requests not matched by a previous rule and
    # passes the request through to AngularJS's Page for processing. The order of other URLS is normally insignificant.
    url('^.*$', IndexView.as_view(), name='index'),
]