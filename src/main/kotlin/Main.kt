import java. io. File
import java. io. InputStream
import java.nio.file.attribute.FileAttribute

fun main(args: Array<String>) {
    val fileName = "secretWords.txt"
    val file = File(fileName)
    val isFileCreated: Boolean = file.createNewFile()

    if(isFileCreated){
        fillFile(file)
    }
    do{
        println("do you have any words you would like to add(y/n): ")
        val stringInput = readLine()
        if(stringInput == "y"){
            println("what word would you like to add: ")
            val addedSecret = readln()
            writeToFile(file, addedSecret)
        }
    }while(stringInput == "y")



    val inputStream: InputStream = File ("secretWords.txt").inputStream()
    val inputString = inputStream.reader().use {it.readText()}
    val secretWords = fileToArray(inputString)

    val secret = secretWords[secretWords.indices.random()]
    var displayWord = ""
    var lettersToGuess = "abcdefghijklmnopqrstuvwxyz"

    for(letter in secret){
        displayWord += "-"
    }

    val gallows = arrayOf("________",
                          "|      |",
                          "|",
                          "|",
                          "|",
                          "|",
                          "guess: $displayWord",
                          "unguessed letters $lettersToGuess"
    )
    var gameEnd = false
    while(!gameEnd){//displayWord != secret || gallows == "|"){
        displayGallows(gallows)
        println("please enter your 1 character or guess the word: ")
        val guess = readln()
        var index = 0
        var correctGuess = false

        if(guess.length == 1){
            for(letter in lettersToGuess){
                if(letter == guess[0]){
                    lettersToGuess = lettersToGuess.substring(0,index) + lettersToGuess.substring(index+1)
                    gallows[7] = "unguessed letters $lettersToGuess"
                    correctGuess = false
                    break
                }
                else{
                    correctGuess = true
                }
                index += 1
            }
            index = 0
            for(letter in secret){
                if(letter == guess[0]){
                    displayWord = displayWord.substring(0,index) + guess[0] + displayWord.substring(index+1)
                    gallows[6] = "guess: $displayWord"
                    correctGuess = true
                }
                index += 1
            }

            if(!correctGuess){
                hangPiece(gallows)
            }
            if(displayWord == secret) {
                gameEnd = true
                println("secret word was $secret")
            }
            if(gallows[4].length == 11){
                gameEnd = true
                displayGallows(gallows)
            }
        }
        else {
            if(secret == guess.lowercase()){
                gameEnd = true
                println("secret word was $secret")
            }
            else{
                hangPiece(gallows)
            }
        }

    }


    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
}

 fun displayGallows(gallows: Array<String>){
     for(row in gallows){
         println(row)
     }
 }

fun hangPiece(gallows: Array<String>){
    if(gallows[2].length == 1){
        gallows[2] += "    (\\"
    }
    else if(gallows[2].length == 7){
        gallows[2] += "_/)"
    }
    else if(gallows[3].length == 1){
        gallows[3] += "    ('-')"
    }
    else if(gallows[4].length == 1){
        gallows[4] += "   (\")"
    }
    else if(gallows[4].length == 7){
        gallows[4] += "_(\")"
    }
}

fun fillFile(file: File){
    file.writeText("alpha, hands, saute, strife, based")
}

fun fileToArray(file: String): Array<String> {
    val delim = ", "
    val arr = file.split(delim).toTypedArray()

    return arr
}

fun writeToFile(file: File, add: String){
    file.appendText(", $add")
}