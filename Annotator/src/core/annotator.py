#!/usr/bin/python 
# -*- coding: utf-8 -*-
import sys
import time

class Pair:
    pass

def printPair(Pair):
    print(str(Pair.begin) + "," + str(Pair.end))

              
words = myQuestion.lower().split(" ")

begin_time = time.time()

dict = {}

def letterKey(x):
    return ord(x) 

base = 101
p = sys.maxsize

#funzione hash (Rabin Karp rolling hash)
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
        dict.get(mySum).append(element.lower().rstrip("\n"))

allElementDict = {}

#hashing degli elementi presi da file o da db(prime due lettere)
for line in dataset:
    mySum = 0
    index = 0
    strippata = line.rstrip("\n").lower()
    if strippata.__len__()>1:
        while index<2:
            mySum+= hashing(strippata[index], base, index,p)
            index=index+1 
        if not allElementDict.get(mySum):
            allElementDict[mySum]=[]
        allElementDict.get(mySum).append(strippata)
                

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
            foundElement.append(i + 1) 
        if (ord(text[skipValue])>256):
            skipValue += patternLength
        else:
            skipValue += skipList[ord(text[skipValue])]
    return foundElement


text = myQuestion.lower()

solutionArray = {}
positionValues = []

for element in dict:
    if allElementDict.get(element):
        for indexElement in allElementDict.get(element):
            positionValues=research(indexElement, text, 256)
            if (positionValues.__len__() >0):
                if not solutionArray.get(indexElement):
                    solutionArray[indexElement]=[]
                for element in positionValues:
                    pair = Pair()
                    pair.begin = element
                    pair.end = element + (indexElement.__len__()-1)
                    solutionArray.get(indexElement).append(str(pair.begin) + "-" + str(pair.end))
                
final_time = time.time()