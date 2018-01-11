package quin0234.algonquincollege.com.androidfinal

/**
 * Created by paulquinnell on 2018-01-10.
 */
class buildingFeed(val buildings: Array<Building>)

class Building ( val buildingId : Int, val nameEN : String,
                 val categoryFR : String, val categoryId : Int,
                 val image: String, val longitude : Float,
                 val latitude : Float , val imageDescriptionEN : String,
                 val saturdayStart : String, val saturdayClose : String,
                 val descriptionEN: String, val addressEN: String)



/*
_id: "5a539e2dfea96e003bc4cc96",
buildingId: 1,
nameEN: "Abbotsford House",
categoryFR: "Centres communautaires/de soins",
categoryId: 7,
longitude: -75.6863285,
latitude: 45.3987859,
postalCode: "K1S 5G6",
province: "ON",
city: "Ottawa",
imageDescriptionFR: "Extérieur d'une résidence de deux étages de style néogothique, désignée bien patrimonial.",
imageDescriptionEN: "Exterior view of a Gothic revival style, two-storey designated heritage property.",
image: "images/include/doors/Abbotsford House.jpg",
sundayClose: null,
sundayStart: null,
saturdayClose: "2017-06-03 16:00",
saturdayStart: "2017-06-03 10:00",
categoryEN: "Community and/or Care centres",
descriptionFR: "Construit en 1872 dans un style néogothique, cet édifice patrimonial a été acheté en 1889 afin de servir de résidence « pour aider les personnes non autonomes en raison de leur âge, d'une déficience ou de la malchance ». Après des rénovations en 1928 (dont l'ajout d'une aile) et au milieu des années 1970, il est devenu aujourd'hui un centre d'activités pour les adultes de plus de 55 ans. Trois manteaux de cheminée d'origine en marbre ornent encore le salon et la salle à manger. Le rez-de-chaussée est ouvert au public.",
descriptionEN: "Built in 1872 in gothic revival style, the designated heritage property was purchased in 1889 as a home "to assist those who, through age, infirmity and misfortune, were unable to help themselves." Renovated in 1928 (including the addition of a wing) and again in the mid-70s, it's now an active centre for adults over 55. Three original marble fireplace mantels still adorn the parlour and dining room. The main level is open for viewing.",
addressFR: "950, rue Bank",
addressEN: "950 Bank St",
isNewBuilding: false,
nameFR: "Résidence Abbotsford",*/