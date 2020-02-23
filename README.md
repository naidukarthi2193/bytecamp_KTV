# DigiSaatBara : Geo-fencing and Efficient Crop Prediction for farmers using land Documents


The Goverment Documents like Saat Bara Documenents and Soil Health Card contain large amount of Unstructured Data. These data can be made to put to use for the Usage of Farmers

![Flow](images/flow.jpg)

<br><br>
<img src="https://github.com/naidukarthi2193/bytecamp_KTV/blob/master/images/saatbara.jpg" align="right"
width="300" height="400">


The SaatBaara Documents contain detailed reports of the following factors in land usage

- Total Area of Land
- Land Under Cultivation (Per Crop Season)
  - Crop Season
  - Crop Cultivated
  - Area Cultivated (In Hectares)
  - Irrigation Water Source
  - Irrigated Area (In Hectares)
  - Mixed Crop / Single Crop
- Land Not Available For Cultivation 
  - Area not cultivated (In Hectares) 
  - Usage 
  - Irrigation Equipment
- Land Landmarks for Demarcation
<br>
<br>


The Soil  Health Card Provides complete information about the Soil Contents 
<br>

<img src="images/soil.jpg" align="center">


The project focuses on creation of the entire system for data collection and generating detailed reports after data analysis from the collected and predicted data. <p>
The approach consists of a mobile app and a website which has two users the government admin and landholders. The main focus of the application is extracting information from 7 / 12 for geofencing of land and crop prediction for farmers and generating a final report for landholders which contains</p>

 - Information about the land 
 - Soil health card of the land 
 - History of the crops grown on the land 
 - Prediction of crops that can be grown 
 - Analysis of the costs incurred for each predictions 
 - Area circumscribed by the geo-fence coordinates on the map
  

## Android App
 Our Android uses Google SDKs for Marking the Geo Coordinates of the Land from the Users 

 <img src="images/app1.jpg" align="left"  width="250" height="400"> 
 <img src="images/app2.jpg"  align="left" width="250" height="400">
 <img src="images/app3.jpg"   align="left" width="250" height="400">

 To Reduce False Inputs the Area Marked in the Polygon should be within  ~2% of the Area mentioned in the Land Documents

## Web App

The Crop Prediction helps the Farmers to get an Idea of the Resources requierments of the Farmers

```bash
pip install -r requirements.txt
```