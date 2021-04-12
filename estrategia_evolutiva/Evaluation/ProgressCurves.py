# coding=utf-8
import numpy as np
import matplotlib.pyplot as plt

files = []
runs = 20
generations = 2000

for i in range(runs):
    lines = []
    with open('./progressCurves'+str(i)+'.txt', 'r') as reader:
        for line in reader:
            line = line.strip()
            fitness = float(line.split()[1])
            lines.append(fitness)
    files.append(lines)

fitnessAvg = []
for j in range(generations):
    fitnessSum = 0
    for k in range(runs):
        fitnessSum += files[k][j]

    fitnessAvg.append(fitnessSum/runs)

#avgFitnessFile = open('avgFitness/avgFitnessEsferaOneMuLambda.txt', 'w')
#avgFitnessFile = open('avgFitness/avgFitnessEsferaNMuLambda.txt', 'w')
#avgFitnessFile = open('avgFitness/avgFitnessEsferaOneMuPlusLambda.txt', 'w')
#avgFitnessFile = open('avgFitness/avgFitnessEsferaNMuPlusLambda.txt', 'w')

#avgFitnessFile = open('avgFitness/avgFitnessSchwefelOneMuLambda.txt', 'w')
#avgFitnessFile = open('avgFitness/avgFitnessSchwefelNMuLambda.txt', 'w')
#avgFitnessFile = open('avgFitness/avgFitnessSchwefelOneMuPlusLambda.txt', 'w')
avgFitnessFile = open('avgFitness/avgFitnessSchwefelNMuPlusLambda.txt', 'w')
for i in range(len(fitnessAvg)):
    avgFitnessFile.write(str(fitnessAvg[i])+'\n')

x = np.arange(500)

plt.ylabel("Fitness")
plt.xlabel("Generaciones")
plt.yscale('log')
plt.margins(0.01)
plt.plot(x, fitnessAvg[0:500], 'g')
#plt.savefig("progressCurvesEsferaOneMuLambda.png")
#plt.savefig("progressCurvesEsferaNMuLambda500.png")
#plt.savefig("progressCurvesEsferaOneMuPlusLambda.png")
#plt.savefig("progressCurvesEsferaNMuPlusLambda500.png")

#plt.savefig("progressCurvesSchwefelOneMuLambda500.png")
#plt.savefig("progressCurvesSchwefelNMuLambda500.png")
#plt.savefig("progressCurvesSchwefelOneMuPlusLambda500.png")
plt.savefig("progressCurvesSchwefelNMuPlusLambda500.png")
plt.show()
