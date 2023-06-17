[![Typing SVG](https://readme-typing-svg.herokuapp.com?font=Montserrat&weight=600&size=25&color=010304&background=FFFFFF00&center=%D0%9B%D0%9E%D0%96%D0%AC&vCenter=%D0%9B%D0%9E%D0%96%D0%AC&width=435&lines=HappyPetBot)](https://git.io/typing-svg) 


A Telegram bot that answers people's popular questions about what you need to know and be able to do in order to take an animal from a shelter.


## Development team: <br>
- [TamaraZolotovskaya](https://github.com/TamaraZolotovskaya)<br>
- [dvlunev](https://github.com/dvlunev)<br>
- [sofibesedina444](https://github.com/sofibesedina444)<br>
- [LeoRoA](https://github.com/LeoRoA)<br>
- [MoXuT0](https://github.com/MoXuT0)<br>

## Technologies in the project ##
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white) ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white) ![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white) ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) 
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white) ![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white) ![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)  ![Postman](https://img.shields.io/badge/postman-%23ED8B00.svg?style=for-the-badge&logo=postman&logoColor=white)

Backend:
- Java 17
- Maven
- Spring Boot
- Spring Web
- Spring Data
- Spring JPA
- GIT 	 
- REST
- Swagger 	
- Stream API

SQL:
- PostgreSQL 

### User`s functionality: ###

**Stage 0. Request definition** 
<details>
<summary>Description</summary>

*This is the entry point for the bot to communicate with the user.* 

- The bot welcomes a new user, talks about itself and can display a menu to choose what request the user came with:
     - Find out information about the shelter (stage 1).
     - How to adopt a dog from a shelter (stage 2).
     - Submit a pet report (step 3).
     - Call a volunteer.
- If none of the options are suitable, then the bot can call a volunteer.
- If the user has already contacted the bot before, then a new request begins with the selection of the request with which the user came.
</details>

**Stage 1. New user consultation** 
<details>
<summary>Description</summary>

*At this stage, the bot should provide introductory information about the shelter: where it is located, how and when it works, what are the rules for entering the shelter, the rules for staying inside and communicating with dogs.*

- The bot welcomes the user.
- The bot can tell you about the shelter.
- The bot can give the shelter's work schedule and address, directions.
- The bot can issue general safety recommendations on the territory of the shelter.
- The bot can accept and record contact details for communication.
- If the bot cannot answer the client's questions, then you can call a volunteer.
</details>

**Stage 2. Consultation with a potential owner of an animal from a shelter** 
<details>
<summary>Description</summary>

*At this stage, the bot helps potential adopters of a dog from a shelter deal with bureaucratic (how to draw up a contract) and everyday (how to prepare for life with a dog) issues.*

*The main task is to give the most complete information about how a person has to prepare for a meeting with a new family member.*

- The bot welcomes the user.
- The bot can issue the rules for getting to know the dog before you can pick it up from the shelter.
- The bot can issue a list of documents required in order to adopt a dog from a shelter.
- The bot can issue a list of recommendations for transporting the animal.
- The bot can give a list of recommendations for home improvement for a puppy.
- The bot can give a list of recommendations for home improvement for an adult dog.
- The bot can issue a list of recommendations for home improvement for a dog with disabilities (vision, movement).
- The bot can give cynologist`s advices on the initial communication with the dog.
- The bot can issue recommendations on proven dog handlers for further referral to them.
- The bot can give a list of reasons why they can refuse and not allow the dog to be taken from the shelter.
- The bot can accept and record contact details for communication.
- If the bot cannot answer the client's questions, then you can call a volunteer.
</details>

**Stage 3. Keeping a pet** 
<details>
<summary>Description</summary>

*After the new adopter took the dog from the shelter, he is obliged to send information about how the animal feels in the new place within a month. The daily report includes the following information:*

- *Photo of the animal.*
- * Animal diet. *
- *General well-being and getting used to a new place.*
- *Change in behavior: abandoning old habits, acquiring new ones.*

*The report must be sent every day, there are no restrictions in the day on the time of submission of the report. Once every two or three days, volunteers review all submitted reports. If the adopter did not fill out the report properly, the volunteer can give feedback through the bot in the standard form: “Dear adopter, we noticed that you are not filling out the report in as much detail as necessary. Please take a more responsible approach to this activity. Otherwise, the shelter volunteers will be required to personally check the conditions of the dog.”*

*A user enters the database of new adopters through a volunteer who brings him there. The task of the bot is to accept information as input and, if the user does not send information, remind about it, and if more than 2 days pass, then send a request to the volunteer to contact the adopter.*

*Once the 30 day period ends, the volunteers decide whether the dog stays with the owner or not. The trial period may be passed, may be extended for any number of days, or may not be passed.*

- The bot can send a daily report form.
- If the user sent only a photo, then the bot can request a text.
- If the user sent only text, then the bot can request a photo.
- The bot can issue a warning that the report is filled out poorly (made by a volunteer):
“*Dear adoptive parent, we have noticed that you are not filling out the report in as much detail as necessary. Please take a more responsible approach to this activity. Otherwise, the shelter volunteers will be required to personally check the conditions of the dog.”*
- If the adopter has passed the trial period, then the bot congratulates him with a standard message.
- If the adopter was assigned an additional probation period, the bot informs him and indicates the number of additional days.
- If the adopter has not passed the trial period, the bot notifies him of this and gives instructions on further steps.
- If the bot cannot answer the client's questions, then you can call a volunteer.
</details>

## Demonstration of the Telegram bot ##
A demo of the application can be found [here](https://github.com/TamaraZolotovskaya/HappyDogBot/blob/master/FourTeam_HappyPetBot.mp4)
