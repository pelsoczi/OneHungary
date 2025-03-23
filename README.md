## One Mobile Application Developer Test Assignment - v2

### Task description

We would like you to create an application that shows our users special offers they can purchase on top of their standard plans.
The application must contain three screens:

- **A "dummy" Login screen**- that allows the user the "log in" or continue as "guest" user.
- **The Offers list** - that shows all the available offers with selected information about them.
- **The Offer Details** - that is a more detailed description view about these offers only showing data about a specific offer.

**The Login screen and the Session management (guest or logged in user) is optional for junior developers (Offer list and Offer Details are mandatory).** In case the Login screen and
Session management is not implemented just display all "special" and "non-special" offers on the Offers list screen.

_______________________________________________________

### Login screen

- The screen contains a logo and two buttons: "Login" and "Continue as Guest".
- "Login" simulates as if the user is logged in, and allowed to see "special" offers (special offers will be described later in Offers list view)
- "Continue as Guest" simulates guest mode, where the user only get "non-special" offers.
- Both buttons navigate to Offers list screen, where he user can't navigate back from to the Login screen.
- Navigating "back" from this screen (OS default back navigation) should close the application.

### Offers list screen

- The page title in Header must be „My Offers”. There are no back navigation actions on the header. Header must display a "Log out" button on the right side, that
  navigates opens Login screen.
- All available offers must be shown here (can be infinite). These offers come from an API call. If the response has no offers please show an empty screen with only the
  title remaining. If there are no special offers (or the user logged in as Guest) don’t show that section on the screen, not even the title of the section. Do not display
  empty Offers section either.
- Offers must be separated based on their status. These can be „Normal offers” and „Special offers”. The offers must appear under different titles based on this
  parameter.
- Separated offers must be sorted based on their rank in their own section.
- The tiles showing the individual offers must have the following pieces of information on them:
  - Title: The name of the specific offer. If it’s missing leave it blank, the tile must still be visible.
  - Description: A short information package about the offer. If it’s missing leave it blank, the tile must still be visible.
  - An arrow that is always vertically in the middle of the tile.
- If the „id” or the „rank” parameter is null or empty don’t show those specific offer tiles.
- If the user taps on a tile they must be navigated to the description screen.
- A pull-to-refresh mechanism must be implemented.

### Offer details screen

- The full description of a specific offer must be shown here. These pieces of information come from a specific API call that returns only one Offer based on "id".
- The page title must be the name of the offer
  - Android: The user must able to navigate back to the offers list view by using the device's back button.
- The view must show the following values:
  - The name of the offer. If it’s missing leave it blank.
  - The short description of the offer is the same as shown on the list view. If it’s missing leave it blank.
  - A longer description that is exclusive to this view. If it’s missing leave it blank.
  - Below the long description in case of "special offers" display an image 200x200px from an URL that is available online. The image needs to be aligned in the
    center horizontally and the image scaling needs to be adjusted so the 200x200px are is filled.
- A pull-to-refresh mechanism must be implemented.

_______________________________________________________

### Android Technical requirements

- The project must be an Phone-only project (tablet support is not necessary) and should support api level 23 and up.
- The provided Figma design must be the baseline for sizes, colors, etc. Please use the default font instead of the one in the design. The dimensions must still be the
  same as shown there. The icon for the arrow image can be exported from the project. [Próba feladat](https://www.figma.com/design/twfhfvNzPsD6BUKbHoGtWR/Pr%C3%B3ba-feladat-Android?node-id=0-1&p=f&t=C0LmTUS5Pwrrfmdd-0) – Figma
- Please mock the application by using the provided .json files or create a public API that returns the same files and uses those (eg. https://www.jsonkeeper.com/).
- Please use MVVM architecture.
- For API calls please use Retrofit: https://square.github.io/retrofit/
- JSON parsing should not be custom implementation.
- The project must be written in kotlin and layouts in xml instead of Jetpack Compose.
- Usage of databinding is preferred.
- Usage of necessary 3rd party libraries are allowed
- Usage of dependency injection is required.
- Usage of Rx is preferred but other async solutions for API calls can be used.
- The application should build without errors and warnings and crashes must be avoided.
- If the API calls fail please show an alert with a generic error message.
- Please write at least one unit test for each view that is relevant (eg. list items match, data is correctly mapped, etc.).
- Please use a GitHub repository for the project. Commit at every step where you think it’s necessary or logical.

_______________________________________________________

### Responses

> offers.json
```
{
   "offers":[
      {
         "id":"1",
         "rank":2,
         "isSpecial":true,
         "name":"One time 1 GB",
         "shortDescription":"Let's choose between our data packages. This is a special offer just for you!"
      },
      {
         "id":"2",
         "rank":1,
         "isSpecial":true,
         "name":"One time 300 MB",
         "shortDescription":"Let's choose between our data packages."
      },
      {
         "id":"3",
         "rank":4,
         "isSpecial":false,
         "name":"One time 500 MB",
         "shortDescription":"Let's choose between our data packages."
      },
      {
         "id":"4",
         "rank":3,
         "isSpecial":true,
         "name":"One time 100 MB",
         "shortDescription":""
      },
      {
         "id":"5",
         "rank":null,
         "isSpecial":true,
         "name":"One time 200 MB",
         "shortDescription":"Let's choose between our data packages."
      }
   ]
}
```

> offer_details.json

```
{
   "id":"1",
   "name":"One time 1 GB",
   "shortDescription":"Let's choose between our data packages.",
   "description":"1GB 30-day once-off prepaid data bundle. This option is ideal if you don’t exceed your bundle often and want to take advantage of the lower in-bundle rates. You can buy as many once-off bundles as you wish."
}
```
