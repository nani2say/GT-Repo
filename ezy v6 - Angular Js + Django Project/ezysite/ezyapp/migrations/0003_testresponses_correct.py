# -*- coding: utf-8 -*-
# Generated by Django 1.9 on 2016-02-11 18:52
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('ezyapp', '0002_auto_20160211_1843'),
    ]

    operations = [
        migrations.AddField(
            model_name='testresponses',
            name='correct',
            field=models.BooleanField(default=False),
            preserve_default=False,
        ),
    ]
