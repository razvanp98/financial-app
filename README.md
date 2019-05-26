FINANCIAL TIMES ANDROID APP - Configuration and Running

Initial prerequisites
1. Download and install XAMPP on your local machine.
2. If not installed, get Android Studio from https://developer.android.com/studio.
3. Clone or download the repository.
4. Go through the Android Studio's first-time configuration process, then install the Android 9.0 System Image on Emulator. This is needed for running the application.

Server configuration:

5. Download the PHP script files from the following link: 
6. Extract all the files to "C://xampp//htdocs//financial-times-API" (create "financial-times-API" inside htdocs)
7. Start Apache and MySQL servers.
8. Open phpmyadmin and create the databases as shown in pictures below:
9. On your browser's URL bar, type: localhost/financial-times-API. It will show you the scripts you've extracted earlier.
10. Run the following scripts in this order: fetch_company.php  fetch_crypto.php  update_company.php
11. After running, you'll see that the tables in the database have been populated with data.

Running the application:

12. Open the cloned repository in Android Studio.
13. Open up the following .java source-code files: 
  
    AddProfileClient.java
    ApiClient.java
    ApiClientFavourites.java
    CryptoClient.java
    RemoveFavouriteAPI.java
    ShowFavouritesClient.java
    
14. Open up cmd in Windows and type 'ipconfig', that will show you the LAN IP Address.
15. Replace the IP Address in those files with your own IP Address.
16. Build the application.
    

 
  
  
  