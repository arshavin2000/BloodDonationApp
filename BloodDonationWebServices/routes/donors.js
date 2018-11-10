const express = require('express');
const joi = require('joi');
const router = express();


const courses = [
  {id:1, name:'course1'},
  {id:2, name:'course2'},
  {id:3, name:'course3'},

];

router.get('/',(req, res) => {
res.send([1,2,3]);
});

router.get('/:id', (req,res)=> {
  const course = courses.find(c => c.id === parseInt(req.params.id));
  if(!course) return  res.status(404).send('The course with the given id was not found');
  res.send(course);
});

const schema = {
  name : joi.string().min(3).required(),
};


router.post('/', (req,res)=> {

const result = validateSchema(req);
  if(result.error)
  return   res.status(400).send(result.error.details[0].message);


  const course = {
    id: courses.length +1,
    name : req.body.name
  } ;
  courses.push(course);
  res.send(course);
});

router.put('/:id',(req,res) => {

  const course = courses.find(c => c.id === parseInt(req.params.id));
  if(!course) return res.status(404).send('The course with the given id was not found');
  const result = validateSchema(req);
    if(result.error)
    return res.status(400).send(result.error.details[0].message);

    course.name = req.body.name;
    res.send(course);

});

router.delete('/:id', (req,res) => {
  const course = courses.find(c => c.id === parseInt(req.params.id));
  if(!course) return  res.status(404).send('The course with the given id was not found');
  const index = courses.indexOf(course);
  courses.splice(index,1);
  res.send(courses);

});


function validateSchema(course)
{
  const schema = {
    name : joi.string().min(3).required(),
  };

  return joi.validate(course.body, schema);

}


module.exports = router;
