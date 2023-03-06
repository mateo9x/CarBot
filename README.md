# CarBot

First plugin for Discord #justTesting.  

## Functionalities
- Fun facts `/funfact`
- Quiz `/quiz`
- Car meter `/carmeter`

## Pool questions
- To extend pool of fun facts in game `Fun facts` simply extend file `funFacts.json` in directory `funFacts/{language}`
- To extend pool of questions in game `Quiz` simply extend file `quiz.json` in directory `quiz/{language}`
- To extend pool of car brands in game `Car meter` simply extend file `carBrands.json` in directory `carMeter`

## How to run
- Generate token on `https://discord.com/developers/applications`
- Grant permission `MESSAGE_CONTENT` for Your bot on site mentioned above
- Replace placeholder `$REPLACE_HERE` in file `token.json` with Your generated token
- By default language is set to `en`, also available `pl` - just edit in `configuration.json`
- By default forbidden language is permitted, to change that simply change value of `allowForbiddenLanguage` in `configuration.json`
- Invite bot to Your server
- Simply run CarBot app :)
