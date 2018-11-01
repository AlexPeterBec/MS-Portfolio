
import pyspark.sql.session
from pyspark.ml.feature import StringIndexer, IndexToString, OneHotEncoder
from pyspark.ml.feature import VectorAssembler
from pyspark.ml.classification import RandomForestClassifier
from pyspark.ml import Pipeline
from pyspark.ml.evaluation import MulticlassClassificationEvaluator
from pyspark.ml.tuning import ParamGridBuilder, CrossValidator
from pyspark.sql.functions import *
import pandas as pd
import numpy as np


spark = pyspark.sql.session.SparkSession.builder \
    .master("local") \
    .appName("KaggleCoverType") \
    .getOrCreate()

train_data = spark.read.csv('all-data/train-set.csv', header=True, inferSchema=True)
test_data = spark.read.csv('all-data/test-set.csv', header=True, inferSchema=True)

train_data.show(n=5)
