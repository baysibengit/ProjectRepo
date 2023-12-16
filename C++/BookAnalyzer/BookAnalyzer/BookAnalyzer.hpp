//
//  BookAnalyzer.hpp
//  BookAnalyzer
//
//  Created by Ben Baysinger  on 9/6/23.
//

#ifndef BookAnalyzer_hpp
#define BookAnalyzer_hpp
#include <string>
#include <stdio.h>
#include <iostream>
#include <vector>
#include <cstdlib>
#include <fstream>
#include <algorithm>

unsigned long wordCount(std::vector<std::string> vector);
int charCount(std::vector<std::string> vector);
std::string titleFinder (std::vector<std::string> book);
std::string authorFinder (std::vector<std::string> book);
std::string shortestWordFinder (std::vector<std::string> vector);
std::string longestWordFinder (std::vector<std::string> vector);
int keyWordCounter (std::vector<std::string> vector, std::string keyWord);
void keyWordData ( std::vector<std::string> book, std::string keyWord);
#endif /* BookAnalyzer_hpp */
