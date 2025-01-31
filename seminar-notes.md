# Notes from the review seminar

[x] Breed is normalized to uppercase, makes no sense to compare toLowercase when detecting if dachshund. 

[x] No need to initialize owner to null in Dog.

[x] There is a .isBlank() method on strings, which removes the need to trim a string when checking if empty. 

[x] Update all strings to static. 

[-] Dogs and owners can use better naming in the DogRegister class. 

[x] It would be nice if the menu was printed on program start. 


- Removed unneccessary .toLowerCase() call in `isDachshund`
- Removed null initiliaization for owner in the dog constructor
- Replaced `String.trim().equals("")` with `String.isBlank()`.
- Created static variables for all prompts, success messages and error messages
- Implemented `printMenu()`

I also received feedback that 'owners' and 'dogs' can use clearer naming in
DogRegister. I believe they are as descriptive as the suggested dogRegister and
ownerRegister, with the benefit of being shorter. I will keep the names of the
variables as is. 

