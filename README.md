
#COMMITS
Go here: https://github.com/knair99/MathQuiz/commits/master

#BUG LIST
1. When turning orientation in middle of a quiz, the old answer still appears
  - fixed - strAnswerSoFar needed to be updated

2. The equals sign only appears for the first question
  - fixed partially through 1

3. App crashes on ENTER pressed on empty state
  - fixed - null & empty checks

4. App crashes on entering input + screen rotate + enter 
  - fixed - null checks

5. Fill answer + Turn orientation + Hit Enter + Turn orientation = OLD answer appears!
  - fixed

6. Screen rotation resets timer
  - fixed

7. Quiz automatically restarts after five seconds of displaying summar
  - fixed - stopped runnable thread (cancel)

8. Crash when you turn summary dialog to another orientation
  - 

#TODO
1. Control questions - need to make sure there are 10
  - done
2. Display question progress on actionbar or something
  - 
3. Do five second question move
  - done
4. Show some indication of correctness aside from Toast
  - done
5. Show some countdown indicator
  - 
6. Calculate score
  - done
7. Show indication of score on a summary dialog
  - done
8. OK Button on above dialog should bring user to home screen
  - done
9. Ancestral Navigation + dialog for it

#NICE
1. Improve layout centering and structure - somehow
2. Background - use nice images
3. Fix all bugs
4. Make dialog box prettier 
5. Give some indication of a count down



