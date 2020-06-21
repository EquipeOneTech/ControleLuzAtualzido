var express = require('express')
const cron = require("node-cron");
var app = express()

/*  param,
    query,
    body
*/



const bodyParser = require('body-parser');
app.use(bodyParser.json());
app.use((request, res, next) => {
    res.header('Access-Control-Allow-Origin', '*')
    res.header('Access-Control-Allow-Methods', 'GET, PUT, POST, DELETE, PATCH, OPTIONS')
    res.header('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept, Authorization')
    next()
})


let valorAtual = 0;


//cron.schedule("1 * * * * *", () => valorAtual += 3);

cron.schedule("*/3 * * * * *", function () {
    valorAtual += 3
});

function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds) {
            break;
        }
    }
}

app.get('/medidorLigth', function (req, response) {
    // let valorFixo = 0

    response.json(
        {
            "valorAtual": valorAtual.toString()
        }
    )

});



app.listen(3097, function () {
    console.log('App de Exemplo escutando na porta 3097')
})


