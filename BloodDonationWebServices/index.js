//dependencies
const express = require('express');
const helmet = require('helmet');
const morgan = require('morgan');
const dbDebugger = require('debug')('app:db');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const app = express();
const multer = require('multer');

//routes
const home = require('./routes/home');
const post = require('./routes/post');
const donor = require('./routes/donor');
const reciever = require('./routes/reciever');
const center = require('./routes/center')

global.__basedir = __dirname;

require('./app/uploadfile/upload.multipartfile.js')(app);



if(app.get('env') === 'development')
{
  app.use(morgan('tiny'));
  console.log("Morgan enable ...");

}
app.use(express.json());
app.use(express.static('public'));
app.use(helmet());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

app.use('/',home);
app.use('/api', post);
app.use('/api', donor);
app.use('/api', reciever);
app.use('/api', center);

app.use('/static/', express.static(__dirname + '/public'));
require('./controller/upload.multipartfile.js')(app);


//mongoose.connect('mongodb://localhost/blooddonation')

mongoose.connect('mongodb://root:root1234@ds063140.mlab.com:63140/blooddonation')
.then(()=> console.log('Connected to mongoDB'))
.catch(err=> console.error("Could not connect to mongoDB",err));

var port     = process.env.PORT || 3000;

var listen = app.listen(port);
console.log('The App runs on port ' + port);

