//
//  BookAnalyzer.cpp
//  BookAnalyzer
//
//  Created by Ben Baysinger  on 9/6/23.
//

#include "BookAnalyzer.hpp"

/*Using unsigned long becuase there can't be negatives and there can be tons of strings in a book*/
//totalWords will equal the size of the vector containing the book
unsigned long wordCount(std::vector<std::string> book){
    int totalWords = book.size() * 1.0;
    return totalWords;
}

/*Initiate variable to zero. Loop through vector containng book and continually add chars to counter being stored in totalChars*/
int charCount(std::vector<std::string> book){
    int totalChars = 0;
    for(std::string VarString : book){
        totalChars += VarString.length();
    }
    return totalChars;
}

//Creating function to find title
std::string titleFinder (std::vector<std::string> book){
    std::string title = "";
/*Initializing at -1 becuase no word will have a negative postiton */
    int titlePos = -1;
    int authorPos = -1;
/*Initialize wordPosition to loop through vector until string "Title:" is found. Then store position in titlePos variable*/
    for(int wordPos = 0; wordPos < book.size(); wordPos++){
        if(book[wordPos] == "Title:"){
            titlePos = wordPos;
        }
/*Record author postion as well through same method and break loop to avoid from unneccesary looping*/
        else if(book[wordPos] == "Author:"){
            authorPos = wordPos;
            break;
        }
    }
/*Add one to titlePos so that "Title:" is not included. Run for loop to iterate in the space between titlePos and authorPos and store strings in variable title*/
    if(titlePos!= -1 && authorPos !=-1){
        for(int i= titlePos+1; i< authorPos; i++){
//Add space to separate words
            title = title + book[i] + " ";
        }
    }
//Guard if title is unknown
    else if(titlePos == -1){
        title = "unknown";
    }
    return title;
}

//Creating Find author function
std::string authorFinder (std::vector<std::string> book){
    std::string author = "";
/*Initializing at -1 becuase no word will have a negative postiton */
    int authorPos = -1;
    int releaseDatePos = -1;
/*Initialize wordPosition to loop through vector until string "Author:" is found. Then store position in authorPos variable*/
    for(int wordPos = 0; wordPos < book.size(); wordPos++){
        if ( book[wordPos] == "Author:"){
            authorPos = wordPos;
        }
/*Record releaseDate postion as well through same method and break loop to avoid from unneccesary looping*/
        else if (book[wordPos] == "Release"){
            releaseDatePos = wordPos;
            break;
        }
    }
/*Add one to authorPos so that "Author:" is not included. Run for loop to iterate in the space between authorPos and releaseDatePos and store strings in variable author*/
    if ( authorPos != -1 && releaseDatePos != -1){
        for (int i = authorPos + 1; i < releaseDatePos; i++){
//Add space to separate words
            author = author + book[i] + " ";
        }
    }
//Guard if author is unknown
    else if (authorPos == -1){
        author = "unknown";
    }
    
    return author;
}

//Creating function to find shortest word in book
std::string shortestWordFinder (std::vector<std::string> book){
//Initializing with "the" because every book has the
    std::string shortestWord = "the";
//Looping through vector to find string
    for(std::string wordChecker : book){
/*checking length of "the" versus every word in vector and replacing it if word is shorter*/
        if (wordChecker.length() < shortestWord.length()){
            shortestWord = wordChecker;
        }
    }
    return shortestWord;
}

//Creating function to find longest word in book
std::string longestWordFinder (std::vector<std::string> book){
//Initializing with "the" because every book has the
    std::string longestWord = "the";
//Looping through vector to find string
    for(std::string wordComp : book){
/*checking length of "the" versus every word in vector and replacing it if word is longer*/
        if (wordComp.length() > longestWord.length()){
            longestWord = wordComp;
        }
    }
    return longestWord;
}

//Create a function to find keyword
int keyWordCounter (std::vector<std::string> book, std::string keyWord){
//Initialize variable
    int keyWordCounter = 0;
//Loop through vector to find key word using for loop
    for ( int i = 0; i < book.size(); i++){
        if (keyWord == book[i]){
            keyWordCounter++;
        }
    }
    return keyWordCounter;
}

//Create a function to report locations of keyword
void keyWordData ( std::vector<std::string> book, std::string keyWord){
//Initialize keyWordLocation and percentage and total chars
    int keyWordLocation =  0;
//Using charCount as helper function
    int totalChars = charCount(book);
    float keyWordPercentage ;
    /*Run a for loop to iterate through vector to find every keyWord occurrence*/
    for ( int i = 0; i < book.size(); i++){
        if ( keyWord == book[i]){
/* Add one to location because indexes start at 0 while char count starts at 1. Below we store the number of chars into KeyWordLocation so keyWordPercentage can calculate off of first char in keyWord/totalChars*/
            keyWordPercentage = ((keyWordLocation+1) * 100.0) / totalChars;
/*Print out word at positon before keyWord, keyWord, and position after keyWord.*/
            std::cout << "  at " << keyWordPercentage << "%: " << "\"" << book[i-1] << " " << keyWord << " " << book[i+1] << "\"";
//Storing char count -1 to counter act the index starting at 0
            keyWordLocation += book[i].length()-1;
        }
        else {
//Store char count in keywordLocation of every other word.
            keyWordLocation += book[i].length();
        }
    }
}
