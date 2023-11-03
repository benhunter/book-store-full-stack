#!/bin/zsh

http post localhost:8080/books title="Harry Potter" author="JK Rowling" favorite=false
http post localhost:8080/books title="To Kill A Mockingbird" author="Harper Lee" favorite=false
http post localhost:8080/books title="The Great Gatsby" author="F. Scott Fitzgerald" favorite=false

