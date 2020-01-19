 # FileSearch
 
 ## Description
This project is small experiment to create text search engine.
This is console application that takes directory path as input,
and traverses all files in it saving all content of files 
into inverted index. Reverse index is Map that has terms as keys 
and saves information about file and position where it 
occurs(not really real position, but rather relative to other terms order in file) 
This is sbt project, so you need to have it installed.
In order to run app, you need to start sbt session `sbt` and run it with `run /home/user/TextFiles/`.
Input argument is the directory that will be indexed and searched, so keep in mind.

## Todo
* Add stemming algorithm
* Create better scoring algorithm
* More test cases
* Property based testing 