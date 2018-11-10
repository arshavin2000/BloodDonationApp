const express = require('express');
const home = express();



home.get('/',(req,res) => {

  res.send('Hello World')

});

module.exports = home;
