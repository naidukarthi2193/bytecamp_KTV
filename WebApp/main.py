from flask import Flask, render_template,request
import plotly
import plotly.graph_objs as go
import pandas as pd
import numpy as np
import json


app = Flask(__name__)
@app.route('/')
def index():
    rain_jf = pd.read_csv("rain_jf.csv")
    rain_jjas = pd.read_csv("rain_jjas.csv")
    rain_mam = pd.read_csv("rain_mam.csv")
    rain_ons = pd.read_csv("rain_ons.csv")
    produce_chart = pd.read_csv("static/produce_chart.csv")

    data_rain_jf = [
        go.Scatter(y = rain_jf.Rainfall, x=rain_jf.Year,name= "Actual" , mode = "lines"),
        go.Scatter(y = rain_jf.Predicted, x=rain_jf.Year,name= "Predicted" , mode = "lines"),
        go.Scatter(y = rain_jf.Lower, x=rain_jf.Year,name= "Lower" ,line=dict(color='grey', width=2, dash='dot')),
        go.Scatter(y = rain_jf.Upper, x=rain_jf.Year,name= "Upper" ,fill='tonexty',line=dict(color='grey', width=2, dash='dot')),
    ]

    data_rain_jjas = [
        go.Scatter(y = rain_jjas.Rainfall, x=rain_jf.Year,name= "Actual" , mode = "lines"),
        go.Scatter(y = rain_jjas.Prediction, x=rain_jf.Year,name= "Predicted" , mode = "lines"),
        go.Scatter(y = rain_jjas.Lower, x=rain_jf.Year,name= "Lower" ,line=dict(color='grey', width=2, dash='dot')),
        go.Scatter(y = rain_jjas.Upper, x=rain_jf.Year,name= "Upper" ,fill='tonexty',line=dict(color='grey', width=2, dash='dot')),
    ]
    data_rain_mam = [
        go.Scatter(y = rain_mam.Rainfall, x=rain_jf.Year,name= "Actual" , mode = "lines"),
        go.Scatter(y = rain_mam.Predicted, x=rain_jf.Year,name= "Predicted" , mode = "lines"),
        go.Scatter(y = rain_mam.Lower, x=rain_jf.Year,name= "Lower" ,line=dict(color='grey', width=2, dash='dot')),
        go.Scatter(y = rain_mam.Upper, x=rain_jf.Year,name= "Upper" ,fill='tonexty',line=dict(color='grey', width=2, dash='dot')),
    ]
    data_rain_ons = [
        go.Scatter(y = rain_ons.Rainfall, x=rain_jf.Year,name= "Actual" , mode = "lines"),
        go.Scatter(y = rain_ons.Predicted, x=rain_jf.Year,name= "Predicted" , mode = "lines"),
        go.Scatter(y = rain_ons.Lower, x=rain_jf.Year,name= "Lower" ,line=dict(color='grey', width=2, dash='dot')),
        go.Scatter(y = rain_ons.Upper, x=rain_jf.Year,name= "Upper" ,fill='tonexty',line=dict(color='grey', width=2, dash='dot')),
    ]

    data_produce_rice = [
        go.Scatter(y =produce_chart.Rice, x=produce_chart.Area,name= "Rice" , mode = "lines")
    ]
    data_produce_sugar = [
        go.Scatter(y = produce_chart.Sugarcane, x=produce_chart.Area,name= "Sugarcane" , mode = "lines")
    ]
    data_produce_cotton = [
        go.Scatter(y = produce_chart.Cotton, x=produce_chart.Area,name= "Cotton" , mode = "lines")
    ]


    
    json_produce_rice = json.dumps(data_produce_rice,cls=plotly.utils.PlotlyJSONEncoder)
    
    json_produce_sugar = json.dumps(data_produce_sugar,cls=plotly.utils.PlotlyJSONEncoder)
    
    json_produce_cotton = json.dumps(data_produce_cotton,cls=plotly.utils.PlotlyJSONEncoder)






    json_rain_jf = json.dumps(data_rain_jf,cls=plotly.utils.PlotlyJSONEncoder)
    json_rain_ijas = json.dumps(data_rain_jjas,cls=plotly.utils.PlotlyJSONEncoder)
    json_rain_mam = json.dumps(data_rain_mam,cls=plotly.utils.PlotlyJSONEncoder)
    json_rain_ons = json.dumps(data_rain_ons,cls=plotly.utils.PlotlyJSONEncoder)
    return render_template("index.html" , json_rain_jf = json_rain_jf,json_rain_ijas=json_rain_ijas,json_rain_mam=json_rain_mam,json_rain_ons=json_rain_ons,json_produce_cotton=json_produce_cotton,json_produce_sugar=json_produce_sugar,json_produce_rice=json_produce_rice)
if __name__ == '__main__':
    app.run(debug=True)