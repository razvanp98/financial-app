## Environment setup

## **Prerequisites**

1. 
2. If not installed, get Android Studio from https://developer.android.com/studio.

## Server configuration

5. Download the PHP script files from the following link: 
6. Extract all the files to ***C:/xampp/htdocs/financial-times-API***'(create ***financial-times-API*** folder inside ***htdocs***)
7. Start Apache and MySQL servers.
8. Open **phpmyadmin** (***localhost/phpmyadmin*** in URL bar) and create the databases as shown in pictures below. (*Figure 1*)
9. On your browser's URL bar, type: ***localhost/financial-times-API***. It will show you the scripts you've extracted earlier.
10. Run the following scripts in this order: 
			**fetch_company.php**  
			**fetch_crypto.php**  
			**update_company.php**
12. After running, you'll see that the tables have been populated with data.

## Running the application

12. Open the cloned repository in Android Studio.
13. Open up the following .java source-code files: 
  
    **AddProfileClient.java**
    **ApiClient.java**
    **ApiClientFavourites.java**
    **CryptoClient.java**
    **RemoveFavouriteAPI.java
    ShowFavouritesClient.java**
    
14. Open **Command Prompt** in Windows and type ***ipconfig*** that will show you the LAN IP Address.
15. Replace the IP Address in those files with your own IP Address where indicated.
16. Build the application.
17. Run the app in the emulator.
    



***Figure 1***
