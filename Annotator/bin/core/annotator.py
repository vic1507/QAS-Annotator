#!/usr/bin/python 
# -*- coding: utf-8 -*-
import sys
import time

class Pair:
    pass

def printPair(Pair):
    print(str(Pair.begin) + "," + str(Pair.end))

begin_time = time.time()

dict = {}

def letterKey(x):
    return ord(x) 

base = 101
p = sys.maxsize

#funzione hash (Rabin fingerprinting)
def hashing (letter,base,index,p):
    return (letterKey(letter)*(base**(index)))%p

#hashing delle parole in input (prime due lettere)
for element in words:
    if element.__len__()>1:
        index = 0
        mySum = 0
        for letter in element:
            if index<2:
                index = index+1       
                mySum+=hashing(letter,base,index-1,p)
        if not dict.get(mySum):
            dict[mySum] = []
        dict.get(mySum).append(element.rstrip("\n").encode("utf-8"))


allElementDict={}

if (writeOnModel):
    
    writeModel = open("src/models/hashModel.txt","w")

    #hashing degli elementi presi da file o da db(prime due lettere)
    for line in dataset:
        mySum = 0
        index = 0
        strippata = line.rstrip("\n")
        if strippata.__len__()>1:
            while index<2:
                mySum+= hashing(strippata[index], base, index,p)
                index=index+1 
                if not allElementDict.get(mySum):
                    allElementDict[mySum]=[]
                allElementDict.get(mySum).append(strippata)
            writeModel.write(str(mySum) + "::"+strippata.encode("utf-8") + "\n")
    writeModel.close()

else:
    inputFromFile = open("src/models/hashModel.txt","r")
    for line in inputFromFile.readlines():
        data = line.rstrip("\n").split("::")
        key = int(data[0])
        value = data[1]
        if not allElementDict.get(key):
            allElementDict[key] = []
        allElementDict.get(key).append(value)
    inputFromFile.close()
    
#algoritmo di ricerca (boyyer more hoolands)
def research(pattern, text, charachterRange):
    foundElement = []
    patternLength = len(pattern)
    textLength = len(text)
    if patternLength > textLength:
        return foundElement
    skipList = []
    for i in range(charachterRange): 
        skipList.append(patternLength)
    for i in range(patternLength - 1): 
        skipList[ord(pattern[i])] = patternLength - i - 1
    skipValue = patternLength - 1
    while skipValue < textLength:
        j = patternLength - 1
        i = skipValue
        while j >= 0 and text[i] == pattern[j]:
            j -= 1
            i -= 1
        if j == -1: 
            if (i == -1) and ((textLength==patternLength) or (text[i+patternLength+1]==" ")):
                foundElement.append(i + 1) 
            elif (text[i]==" "):    
                if (i+patternLength+1>=textLength): 
                    foundElement.append(i + 1)
                elif(text[i+patternLength+1]==" ") or (text[i+patternLength+1]=="?") or (text[i+patternLength+1]=="."):
                    foundElement.append(i+1)
        if (ord(text[skipValue])>256):
            skipValue += patternLength
        else:
            skipValue += skipList[ord(text[skipValue])]
    return foundElement


solutionArray = {}
positionValues = []

for element in dict:
    if allElementDict.get(element):
        for indexElement in allElementDict.get(element):
            positionValues=research(indexElement,myQuestion, 256)
            if (positionValues.__len__() >0):
                if not solutionArray.get(indexElement):
                    solutionArray[indexElement]=[]
                for element in positionValues:
                    pair = Pair()
                    pair.begin = element
                    pair.end = element + (indexElement.__len__()-1)
                    solutionArray.get(indexElement).append(str(pair.begin) + "-" + str(pair.end))
                
final_time = time.time()