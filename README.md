![spell checker baseline logo](spell-checker-baseline-logo.png)</br>
-----------------------
A baseline for spell checkers that does not take into account context (+ with I/O capabilities).</br>
To initialise the spell checker create an instance of ContextFreeSpellChecker and pass in as arguments a .txt file containing the dictionary (format = one word on each line).The default dictionary is US English. Second pass in the keyboard layout you expect the user to be using. Note that it is qwerty by default. (For more about the format, just check the qwerty.txt file)</br>
```java
 ContextFreeSpellChecker checker = new ContextFreeSpellChecker();
```

```java
System.out.println("Possible replacement word for rituialisn based LD:\n" + checker.LDCheck("rituialisn"));

System.out.println("Possible replacement word for English based on LD:\n" + checker.LDCheck("English"));

System.out.println("Possible replacement word for rituialisn: based on IBD\n" + checker.IndexBasedCheck("rituialisn"));

System.out.println("Possible replacement word for English: based on IBD\n" + checker.IndexBasedCheck("English"));
```
Ideas for potential improvements: </br>
- See if taking substrings helps.
- Use a combination of both LD and IB.



        
