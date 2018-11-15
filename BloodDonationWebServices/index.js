//dependencies
const express = require('express');
const helmet = require('helmet');
const morgan = require('morgan');
const dbDebugger = require('debug')('app:db');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const app = express();
//routes
const home = require('./routes/home');
const post = require('./routes/post');
const donor = require('./routes/donor');


if(app.get('env') === 'development')
{
  app.use(morgan('tiny'));
  console.log("Morgan enable ...");

}
app.use(express.json());
app.use(express.static('public'));
app.use(helmet());

app.use('/',home);
app.use('/api', post);
app.use('/api', donor);




mongoose.connect('mongodb://root:root1234@ds063140.mlab.com:63140/blooddonation')
.then(()=> console.log('Connected to mongoDB'))
.catch(err=> console.error("Could not connect to mongoDB",err));



const port = process.env.PORT || 3000;
app.listen(port,()=>console.log(`Listening on port ${port} ...`));
