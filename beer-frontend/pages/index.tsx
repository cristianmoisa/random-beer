import fetch from "node-fetch";
import {
  Button,
  Typography,
  Paper,
  Container,
} from "@material-ui/core";
import React from "react";

const topDiv = {
  padding: "50px",
  margin: "100px 20px",
  display: "flex",
  justifyContent: "center"
};

const bottomDiv = {
  display: "flex",
};

const leftDiv = {
  display: "flex",
  maxWidth: "450px"
};

const image = {
  maxWidth: "inherit"
}

const beerNameDiv = {
  marginLeft: "100px",
  padding: "20px",
  marginBottom: "20px"
}

const beerDetailsDiv = {
  padding: "15px",
  display: "column",
  maxWidth: "450px",
  marginLeft: "100px"
};

export type IndexPageProps = {
  document: any;
};

const RandomBeer = ({document} : IndexPageProps) => {

  const [result, setResult] = React.useState(document);
  const getRandomBeer = async () => {
    const getBeer = await fetch(`http://localhost:8080/api/beers/random`, {method: "GET"});
    const document = await getBeer.json()
    setResult(document)
    return document;
  }
    return(
      <Container maxWidth="md">
        <Paper elevation={3} >
          <div style={topDiv}>
              <Typography variant="h4">The Random Beer App</Typography>
              <Button style={{marginLeft: "50px", backgroundColor: "#95b3d4"}} 
                variant="contained" onClick={() => { getRandomBeer()}}>Show Another Beer
              </Button>
          </div>
        </Paper>
        <div style={bottomDiv}>
          <Paper elevation={3} style={leftDiv}>
            <img style={image} src={result.beerImageUrl} alt="image"></img>
          </Paper>
          {result != null ?
          <div>
            <Paper elevation={3} style={beerNameDiv}>
                  <h3 style={{fontSize: "24px"}}>{result.beerName}</h3> 
            </Paper>
            <Paper elevation={3} style={beerDetailsDiv}>
                  <p style={{fontWeight: 700}}>Alcohol %: {result.alcoholPercentage}</p>
                  <p style={{fontWeight: 700}}>Brewed in: {result.breweryLocation}</p>
                  <p style={{textOverflow: "word-wrap"}}>{result.description}</p>
            </Paper>
          </div>
        : ""}
        </div>
      </Container>
    )
}
  
RandomBeer.getInitialProps = async () =>{
  const getBeer = await fetch(`http://beer-app:8080/api/beers/random`, {method: "GET",});
  const document = await getBeer.json()
  return { document };
};

export default RandomBeer;