#### Stuff I Want to Add
It would be nice to have the program factor in the orientation of the caps. To do that it should use a vector of points in each color unit and compare them against each other

#### Possible Optimizations
The next state switch should be prioritized based on what is lowest
The evaluation seems to be overkill especially if you are only swapping two tops. you should only need to evaluate the new tops instead of the entire board every time. This is particularly important if I'm doing the vector approach