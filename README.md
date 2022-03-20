[![forthebadge](https://forthebadge.com/images/badges/built-for-android.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/contains-tasty-spaghetti-code.svg)](https://forthebadge.com)
[![Linkedin](https://img.shields.io/badge/Linkedin-Atharva%20Hudlikar-blue?style=for-the-badge&logo=linkedin)](https://www.linkedin.com/in/atharva-hudlikar/)
# Mercury
An Android App with Ruby on Rails api backend for shared watch lists between friends.<br>

## How the app works
![mercury drawio (1)](https://user-images.githubusercontent.com/36445600/159156283-d4f084c6-4609-434c-9ffb-c67afcc33ae2.png)

## Server Independence
You can add your own server (just basic CRUD server will do) in the app by changing the url in the app. They are located as follows:<br>
* app/src/main/java/com/a_team/mercury/MainActivity.java (Line 44)
* app/src/main/java/com/a_team/mercury/CardDataAdapter.java (Line 170, 198)
* app/src/main/java/com/a_team/mercury/SplashScreen.java (Line 27)

Here is a sample data point sent by the server: <br>
```
{
    "status": "SUCCESS",
    "message": "Loaded items",
    "data": [
        {
            "id": 16,
            "title": "Spiderman No Way Home",
            "url": "https://youtu.be/ZYzbalQ6Lg8",
            "value_type": "movie",
            "created_at": "2022-02-13T15:10:31.003Z",
            "updated_at": "2022-03-20T06:52:41.083Z",
            "watched": 0
        }
    ]
}
```

## App Screenshots
<table sytle="border: 0px;">
<tr>
<td><img width="200px" src="https://user-images.githubusercontent.com/36445600/159157072-763efa44-e57f-4abf-b502-2f287f4f7fc9.jpeg" /></td>
<td><img width="200px" src="https://user-images.githubusercontent.com/36445600/159157088-7273f48c-6a56-4df8-81d2-171c114de907.jpeg" /></td>
<td><img width="200px" src="https://user-images.githubusercontent.com/36445600/159157102-5dbb2952-ac89-403e-9787-71aa69978951.jpeg" /></td>
<td><img width="200px" src="https://user-images.githubusercontent.com/36445600/159157110-f063f2d5-5be6-48fd-8e5b-1282488ab9c2.jpeg" /></td>
</tr>
</table>

## License
[![License](http://img.shields.io/:license-mit-blue.svg?style=flat)](http://badges.mit-license.org)<br>
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
