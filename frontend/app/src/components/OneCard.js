import React from 'react';
import { makeStyles } from "@material-ui/core/styles";
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardContent from '@material-ui/core/CardContent';
import Typography from '@material-ui/core/Typography';


const useStyles = makeStyles((theme) => ({
    card_root: {
      maxWidth: 305,
    },
  }));

export default function OneCard(props){
    const classes = useStyles();

    return(
        <Card className={classes.card_root} style={{marginLeft:"auto", marginRight:"auto"}}>
            <CardActionArea>
                <CardContent>
                <Typography variant="h2" component="h2">
                    {props.stat}
                </Typography>
                <Typography gutterBottom variant="body2">
                    {props.particle}
                </Typography>
                </CardContent>
            </CardActionArea>
        </Card>
    )
}