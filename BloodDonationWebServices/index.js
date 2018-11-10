//dependencies
const express = require('express');
const helmet = require('helmet');
const morgan = require('morgan');
const dbDebugger = require('debug')('app:db');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');
const app = express();
//routes
const donors = require('./routes/donors');
const home = require('./routes/home');
const post = require('./routes/post');

if(app.get('env') === 'development')
{
  app.use(morgan('tiny'));
  console.log("Morgan enable ...");

}
app.use(express.json());
app.use(express.static('public'));
app.use(helmet());

app.use('/',home);
app.use('/api/donors',donors);
app.use('/api', post)



mongoose.connect('mongodb://127.0.0.1:27017/dba')
.then(()=> console.log('Connected to mongoDB'))
.catch(err=> console.error("Could not connect to mongoDB",err));



const port = process.env.PORT || 3000;
app.listen(port,()=>console.log(`Listening on port ${port} ...`));
