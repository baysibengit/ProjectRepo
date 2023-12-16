
//  main.cpp
//  BookAnalyzer
//
//  Created by Ben Baysinger  on 8/31/23.
//
#include "BookAnalyzer.hpp"




int main(int argc, const char * argv[]) {
    
//initialize variable to store first command line input (for a file)
    std::string fileName = argv[1];
// initialize variable to store second command line input (for a word)
    std::string keyWord = argv[2];
//    std::string fileName = "/Users/benbaysinger/myGithubRepo/day9/BookAnalyzer/BookAnalyzer/MobyDick.txt";
//    std::string keyWord = "weltering";
// open the file
    std::ifstream bookStream(fileName);

// confirm the file has opened successfully; display error message && return code if not
    if(bookStream.fail()) {
        std::cout << "Failed to open file: " << fileName << ".\n";
            exit(1);
        }
        
// initialize vector of strings to store all words read in from input file
    std::vector<std::string> allWords;
// initialize word to store each string
    std::string singleWord;
// while loop that runs true as long as strings are read from the input file successfully
    while (bookStream >> singleWord) {
//fill a vector with strings; each string / word becomes an element of the vector
            allWords.push_back(singleWord);
        }
    
/*Calling functions for titleFinder, authorFinder, wordCount, charCount, and shortest/longest word*/
    std::cout<< "Statistics for " << titleFinder(allWords)<< "; by " << authorFinder(allWords) <<"\n" << "Number of words: " << wordCount(allWords) << "\n" << "Number of characters: " << charCount(allWords) << "\n" << "The shortest word is \"" << shortestWordFinder(allWords) << "\", and the longest word is \"" << longestWordFinder(allWords) << "\"." <<  "\n" << "\n";
    
//Calling function for keyword
    std::cout<< "The word " << "\"" << keyWord << "\"" << " appears " << keyWordCounter(allWords, keyWord) << " times: " << "\n";
//Prints within function 
    keyWordData(allWords, keyWord);
    
// close the file
    bookStream.close();
    std::cout << "\n";
    return 0;
}
