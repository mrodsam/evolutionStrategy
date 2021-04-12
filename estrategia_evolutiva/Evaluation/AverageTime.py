timeSum = 0
runs = 20
with open('./times.txt', 'r') as reader:
    for line in reader:
        line = line.strip()
        time = float(line.split()[0])
        timeSum += time

timeAvg = timeSum/runs
print(timeAvg/1000)
