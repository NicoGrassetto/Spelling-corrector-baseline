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
The reader is strongly advised to use this as a baseline for benchmark testing and NOT as an actual chatbot.
Moreover some obvious improvements can be made. Some of which are:
- Making the chatbot's code more verstatile by allowing the user to use multiple fitness functions.
- There are some errors to fix in the probabilities (which technically does not impact the final result)
- One could take advantage of different data structures to make it more scalable.
- The typo detection could be made more robust by not just calculating its neighbours but also by computing the distance to each possible keycap. This would relax the probability of typo as one is still quite likely to press k rather than p in a typo.



        
