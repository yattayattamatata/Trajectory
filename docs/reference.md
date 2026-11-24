# **Reference Videos and Documents**
<br>

## **JavaFX/SceneBuilder/GUI tools**

### Video that explains how to setup and use JavaFX/Scenebuilder (The video is long but there are chapters that you can use to get to specific items)
&emsp; JavaFX GUI Full Course for Free - https://youtu.be/9XJicRt_FaI?si=0hvEB8xG6s6clooD

### Video that shows how to use LineChart in JavaFX
&emsp; JavaFX LineChart Time-Series Data Tutorial - https://www.youtube.com/watch?v=HWfZPiPu1sI
<br><br><br>


## **SQLite/ JDBC tools**

### Video that explains Databases and SQL (the language used to communicate with a database)
&emsp; You Need to Learn SQL Right Now. (SQL Tutorial for Beginners) - https://www.youtube.com/watch?v=xiUTqnI6xk8

### Video that explains how to integrate SQL into code
&emsp; SQLite Introduction - Beginners Guide to SQL and Databases - https://www.youtube.com/watch?v=8Xyn8R9eKB8

### Video that explains how to use JDBC with Java (not SQLite specific but it still applicable)
&emsp; Jdbc Tutorial for Beginners (Java Database Connectivity) - https://youtu.be/03rDqI6lxdI?si=rdtAW864Nwj9JtPv

### Video that explains some JDBC and Java with SQLite
&emsp; Working with Databases in Java: Jdbc & SQLite - https://youtu.be/0beocykXUag?si=b1d3VL2AluKVN7oX

### Tutorial for how to connect JDBC with SQLite
&emsp; https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
<br><br><br>


## **Enum In Java**

### Video that explains ENUMs (I used this to create the Budget categories in BudgetCategory)
&emsp;Java Enums Explained in 6 Minutes - https://www.youtube.com/watch?v=wq9SJb8VeyM
<br><br><br>


## **Mermaid Modeling Tool**

### Video explaining/demonstrating how to use mermaid in the browser to create class/sequence diagrams
&emsp; https://www.youtube.com/watch?v=aMMDMQvSzmc

### Mermaid documentation (On the left side there are references for swimlanes diagrams (activity diagrams), class diagrams, and sequence diagrams)
&emsp; https://mermaid.js.org/syntax/sequenceDiagram.html
<br><br><br>


## **Stash work with git/github (how to save work when switching repos)**
&emsp; https://git-scm.com/book/en/v2/Git-Tools-Stashing-and-Cleaning.html
<br><br><br>


# Update Your Branch With the Latest main

Replace `your-branch-name` with your own branch name.

## Steps

1. Save your work. Pick one:

   Commit it (recommended):
   ```
   git add -A
   git commit -m "Describe what you changed"
   ```

   Or stash it:
   ```
   git stash push -m "work in progress"
   ```

2. Switch to your branch:
   ```
   git switch your-branch-name
   ```

3. Get the latest from the remote:
   ```
   git fetch origin
   ```

4. Merge main into your branch:
   ```
   git merge origin/main
   ```

5. If you stashed in step 1, bring your work back:
   ```
   git stash pop
   ```

6. Check that it builds, then push:
   ```
   mvn compile
   git push
   ```

## If Git Reports a Conflict

1. Run `git status` and open each file listed under "Unmerged paths".
2. Keep the correct lines and delete the marker lines (`<<<<<<<`, `=======`, `>>>>>>>`).
3. Run `git add` on each fixed file.
4. Run `git commit` to finish the merge.

To cancel the merge and start over:
```
git merge --abort
```

## Do Not

- Do not run `git reset --hard` or `git checkout .`. They erase your unsaved work.
- Do not run `git push --force`.
- Do not work directly on main. Use your own branch.
