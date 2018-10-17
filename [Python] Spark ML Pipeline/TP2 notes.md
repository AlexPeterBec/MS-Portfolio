## Part 1 : Clustering par Kmeans

**Wikipedia:**
Kmeans est une methode de partitionnement des données : Etant donnés des points et un entier K, le but est de diviser les points en K groupes. On les utilise en apprentissage non supervisé pour diviser les observations en K partitions.

**Kmean**
Methode d'initialisation : Kmeans non supervise, quand les clusters sont bien définis, on converge toujours rapidement vers des groupes cohérents. Si dans l'initialisation on choisit un outlier, il va rester tout seul et cela impactera le resultat final.

**WSSSE**
On augmente le nombre de groupes en se rapprochant du nombre de points, la somme des erreurs se rapproche de zero.


## Part 2 : Decision Trees with Scala

- Change Tree depth : N = 5 / 10 / 20... A un certain point la précision n'augmente plus
- Mesure de la précision en variant la mesure d'heterogeneité / Impurity : Entropy et Gini
- Ajout d'un RandomForestClassifier : (20) Accuracy = 0.9299363057324841
- Avec (5) Arbres   : 0.9617834394904459
- Avec (30) Arbres  : 0.9617834394904459
- Avec (100) Arbres : 0.9617834394904459

**Cross Validation**
https://spark.apache.org/docs/2.0.2/ml-tuning.html
