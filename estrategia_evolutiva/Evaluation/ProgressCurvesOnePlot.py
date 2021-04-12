import numpy
import matplotlib.pyplot as plt
from os import listdir

curves = []
generations = 250

mi_path = "./avgFitness/"

for archivo in listdir("./avgFitness/"):
    print(archivo)
    lines = []
    with open(mi_path+archivo, 'r') as reader:
        for line in reader:
            line = line.strip()
            fitness = float(line)
            lines.append(fitness)

    curves.append(lines[0:250])

x = numpy.arange(generations)

plt.ylabel("Fitness")
plt.xlabel("Generations")
plt.yscale('log')
plt.margins(0.01)
plt.plot(x, curves[0], 'b', curves[1], 'g', curves[2], 'y', curves[3], 'r')
plt.legend(['NSteps-(mu,lambda)', 'NSteps-(mu+lambda)', 'OneStep-(mu,lambda)', 'OneStep-(mu+lambda)'])
plt.savefig("schwefel_progressCurves.png")
plt.show()
