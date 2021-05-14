import React, { useEffect } from 'react';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import TextField from '@material-ui/core/TextField';
import { fade, makeStyles } from "@material-ui/core/styles";
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import MenuItem from '@material-ui/core/MenuItem';
import Button from '@material-ui/core/Button';  
import Typography from '@material-ui/core/Typography';
import OneCard from '../components/OneCard.js';
import Service from '../services/Service.js';
const useStyles = makeStyles((theme) => ({
    root: {
      boxShadow: `${fade(theme.palette.primary.main, 0.25)} 0 0 0 2px`,
      overflow: 'hidden',
      borderRadius: 4,
      transition: theme.transitions.create(['border-color', 'box-shadow']),
      color:"white",
      '&$focused': {
        boxShadow: `${fade(theme.palette.primary.main, 0.25)} 0 0 0 2px`,
        borderColor: theme.palette.primary.main,
      },
    },
    focused: {},
  }));

export default function SoloBolo() {

  const [showingPart, setShowingPart] = React.useState(<Col md={9} style={{backgroundColor:"#14213d"}}></Col>);
  const [city, setCity] = React.useState("");
  const [country, setCountry] = React.useState("");
  const [locationRequestData, setLocationRequestData] = React.useState({});
  const [statistics, setStatistics] =React.useState({});
  const [view, setView] = React.useState("");
  const classes = useStyles();



  const handleCity = (event) => {
    setCity(event.target.value);
  };

  const handleCountry = (event) => {
    setCountry(String(event.target.value).toUpperCase());
  };

  const currentInfo = (lat, lng) => {
    Service.currentData(lat, lng).then((res)=>{
      return res.json();
    })
    .then((res) => {
      setStatistics({"aqi":res.particlesAndAQI.aqi,"co":res.particlesAndAQI.co,"dateTime":res.particlesAndAQI.dateTime,"nh3":res.particlesAndAQI.nh3,"no":res.particlesAndAQI.no,"no2":res.particlesAndAQI.no2,"o3":res.particlesAndAQI.o3,"pm10":res.particlesAndAQI.pm10,"pm2_5":res.particlesAndAQI.pm2_5,"so2":res.particlesAndAQI.so2});
      setView("changed");
    });
  }

  useEffect(() => {
    console.log("entrou")
    if(view==="Something wrong"){//alterar componente e mostrar que pedido correu mal
      console.log("fez went wrong")
      setShowingPart(
        <Col md={9} style={{backgroundColor:"#14213d"}}>
          <Row style={{margin:"auto", marginTop:"50px"}}>
            <Typography id="result-error" variant="h3" style={{color:"white"}}>Something went wrong</Typography>
          </Row>
        </Col>
      )
      return;
    }
    if(view==="change" && Object.keys(locationRequestData).length && !Object.keys(statistics).length){//pedido relativo aos dados de poluição
      currentInfo(locationRequestData.lat,locationRequestData.lng);
      return;
    }
    if(view==="changed" && Object.keys(statistics).length){//alterar componente para mostrar informação relativamente ao local pesquiasdo
      setView("");//caso o botao seja pressionado de novo certifica que o useeffect nao é chamado novamente
      setShowingPart(
        <Col md={9} style={{backgroundColor:"#14213d"}}>
          <Row style={{margin:"auto", marginTop:"50px"}}>
            <Typography id="result-description" variant="h3" style={{color:"white"}}>Current Air Quality:{locationRequestData.city}, {locationRequestData.countryCode}</Typography>
          </Row>
          <Row style={{margin:"auto", marginTop:"20px"}}>
            <Col>
              <OneCard particle={"NO"} stat={statistics.no}></OneCard>
            </Col>
            <Col>
              <OneCard particle={"NO2"} stat={statistics.no2}></OneCard>
            </Col>
            <Col>
              <OneCard particle={"O3"} stat={statistics.o3}></OneCard>
            </Col>
          </Row>
          <Row style={{margin:"auto", marginTop:"20px"}}>
            <Col>
              <OneCard particle={"SO2"} stat={statistics.so2}></OneCard>
            </Col>
            <Col>
              <OneCard particle={"PM2_5"} stat={statistics.pm2_5}></OneCard>
            </Col>
            <Col>
              <OneCard particle={"PM10"} stat={statistics.pm10}></OneCard>
            </Col>
          </Row>
          <Row style={{margin:"auto", marginTop:"20px"}}>
            <Col>
              <OneCard particle={"NH3"} stat={statistics.nh3}></OneCard>
            </Col>
            <Col>
              <OneCard particle={"CO"} stat={statistics.co}></OneCard>
            </Col>

          </Row>
          <Row style={{marginTop:"30px"}}><Typography variant="body1" style={{color:"white", margin:"auto"}}>Air Quality Index (1=Good,...,5=Very Poor):{statistics.aqi}</Typography></Row>
        </Col>
      )
      setStatistics({})//apaga estatisticas relativas as particulas
      setLocationRequestData({})//apaga informaçao relativamente a localização para nao fazer if anterior duas vezes
    }
  }, [view, locationRequestData,statistics]); //este useEffect é chamado quando qualquer destas variaveis é alterada

  const getInformation = () => { // pedido da localizaçao que desencadeia todos os pedidos no useEffect
      Service.searchCityCountry(city,country)
      .then((res)=>{
        let value = res.json();
        console.log("value");
        console.log(value)
        if(res.status!==200 || (value.columnNumber===0 && value.columnNumber===0)){
          console.log("set")
          setView("Something wrong");
          return null;
        }
        else{
          return value;
        }
      })
      .then((res) => {
        console.log(res)
        if(!res)
          return;
        console.log("nao retornou")
        setView("change");
        setLocationRequestData({"lat":res.lat,"lng":res.lng,"countryCode":res.country,"city":res.name});
      });
  }

  return (
    <>
        <div style={{height:"100%"}}>
          <Row style={{height:"100%", marginLeft:"auto", marginRight:"auto"}}>
          <Col md={3} style={{backgroundColor:"#2a9d8f"}}>
            <Row>
              <Col>
                <TextField id="cityName" onChange={handleCity} InputProps={{ classes, disableUnderline: true  }} InputLabelProps={{style:{color:"white"}}} style={{marginLeft:"auto", marginRight:"auto", marginTop:"100px", width:"80%"}} label="City" type="search" variant="outlined"/>
              </Col>
            </Row>
            <Row>
              <Col>
                <TextField id="countryName" onChange={handleCountry} InputProps={{ classes, disableUnderline: true  }} InputLabelProps={{style:{color:"white"}}} style={{marginLeft:"auto", marginRight:"auto", marginTop:"20px", width:"80%"}} label="Country Code" type="search" variant="outlined"/>
              </Col>
            </Row>
            <br/>
            <Row>
              <Col>
                <Button variant="outlined" disabled={city===""} style={{marginTop:"100px", marginLeft:"auto", marginRight:"auto"}} onClick={() => {getInformation()}}>Search</Button>
              </Col>
            </Row>
            </Col>
            {showingPart}{/* informaçao relativa aos pedidos que é alterada no useeffect consoante os pedidos */}
          </Row>
        </div>
    </>
  );
}