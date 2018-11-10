const express = require('express');
const helmet = require('helmet');
const morgan = require('morgan');
const donors = require('./routes/donors')
const home = require('./routes/home')
const dbDebugger = require('debug')('app:db');
const mongoose = require('mongoose');
const app = express();



if(app.get('env') === 'development')
{
  app.use(morgan('tiny'));
  console.log("Morgan enable ...");

}
app.use(express.json());
app.use(express.static('public'));
app.use(helmet());
app.use('/api/donors',donors);
app.use('/',home);


mongoose.connect('')
.then(()=> console.log('Connected to mongoDB'))
.catch(err=> console.error("Could not connect to mongoDB",err));



const port = process.env.PORT || 3000;
app.listen(port,()=>console.log(`Listening on port ${port} ...`));
