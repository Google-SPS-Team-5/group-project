package com.google.sps;

import com.google.sps.Business;
import java.net.URL;    
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.Collections;


/**
* Temporary class to hold mock data for testing purposes. Will be removed when we have actual business data in our data and blobstores.
*/
public final class MockDataBusiness {

  /**
  * Default constructor to initialise all the businesses and add them to the mockDataList. (total of 10).
  */
  public MockDataBusiness()
  {
    String businessName = "The Oven Bakes";
    List<String> categoryList = Arrays.asList("Dessert", "Brownies", "Cookies");
    float businessLat = 1.315120F;
    float businessLong = 103.764977F;
    String businessAddr = "3150 Commonwealth Avenue West, 129580";
    String businessLogoUrl = "https://res-1.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_120,w_120,f_auto,b_white,q_auto:eco/v1412921346/nnzir0xhukjpbpmctwy9.jpg";
    List<String> businessPhotoUrlList = Arrays.asList("images/the-oven-bakes/the-oven-bakes-1.PNG","images/the-oven-bakes/the-oven-bakes-2.PNG","images/the-oven-bakes/the-oven-bakes-3.PNG","images/the-oven-bakes/the-oven-bakes-5.PNG");

    String businessDescr = "The Oven Bakes is a local home business that makes mouth-watering brownies and cookies." +
                            "These homemade treats are sure going to sweeten your day." +
                            "Baked goodies are limited in stock and only available for pre-order. Grab yours soon!\n\n" +
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
                            "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
                            "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit" +
                            "esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in" +
                            "culpa qui officia deserunt mollit anim id est laborum.";

    String menuUrl = "https://www.instagram.com/theovenbakes/";
    String businessOrderInfo = "Minimum Order(for delivery): $15\n\n" +
                                "Islandwide Delivery: $10";
    String businessContactUrl = "https://www.instagram.com/theovenbakes/";
    String linkToBusiness = "https://www.instagram.com/theovenbakes/";

    Business mockBusiness1 = new Business(businessName, 
                                            categoryList,
                                            5,
                                            businessLat, 
                                            businessLong, 
                                            businessAddr, 
                                            businessLogoUrl, 
                                            businessPhotoUrlList,
                                            businessDescr,
                                            menuUrl,
                                            businessOrderInfo,
                                            businessContactUrl,
                                            linkToBusiness);

    businessName = "Roti Kirai Singapura";
    categoryList = Arrays.asList("Mains");
    businessLat = 1.351780F;
    businessLong = 103.954720F;
    businessAddr = "Blk 298 Tampines Street 22 Singapore 520298";
    businessLogoUrl = "https://res-1.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_120,w_120,f_auto,b_white,q_auto:eco/v1412921346/nnzir0xhukjpbpmctwy9.jpg";
    businessPhotoUrlList = Arrays.asList("images/the-oven-bakes/the-oven-bakes-1.PNG","images/the-oven-bakes/the-oven-bakes-2.PNG","images/the-oven-bakes/the-oven-bakes-3.PNG","images/the-oven-bakes/the-oven-bakes-5.PNG");

    businessDescr = "A set of roti jala comes with a portion of curry filled with chicken, carrots and potatoes,"+
                    "as well as a salad with half a hardboiled egg, cherry tomatoes and a special homemade dressing.\n" +
                    "Based in Tampines, Singapore! Muslim-owned. Taste the quality. Click link below to order.";

    menuUrl = "https://www.facebook.com/RotiKirai/";
    businessOrderInfo = "A couple set costs S$13 while a festive set goes for S$55";
    
    businessContactUrl = "https://api.whatsapp.com/send?phone=6597849932&text=&source=&data=&app_absent=";
    linkToBusiness = "https://www.instagram.com/roti.kirai.singapura/?utm_source=ig_embed";

    Business mockBusiness2 = new Business(businessName, 
                                            categoryList,
                                            4, 
                                            businessLat, 
                                            businessLong, 
                                            businessAddr, 
                                            businessLogoUrl, 
                                            businessPhotoUrlList,
                                            businessDescr,
                                            menuUrl,
                                            businessOrderInfo,
                                            businessContactUrl,
                                            linkToBusiness);

    businessName = "Paparch Sg";
    categoryList = Arrays.asList("Dessert", "Cakes");
    businessLat = 1.315120F;
    businessLong = 103.764977F;
    businessAddr = "3150 Commonwealth Avenue West, 129580";
    businessLogoUrl = "https://res-1.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_120,w_120,f_auto,b_white,q_auto:eco/v1412921346/nnzir0xhukjpbpmctwy9.jpg";
    businessPhotoUrlList = Arrays.asList("images/the-oven-bakes/the-oven-bakes-1.PNG","images/the-oven-bakes/the-oven-bakes-2.PNG","images/the-oven-bakes/the-oven-bakes-3.PNG","images/the-oven-bakes/the-oven-bakes-5.PNG");

    businessDescr = "The home baker's cheesecake has an oozy, cheesy centre that is liked by many." + 
                    "A whole cheesecake currently goes for S$47 (U.P.)." + 
                    "It is big enough to serve between seven to eight people.";

    menuUrl = "https://www.instagram.com/paparchsg/";

    businessOrderInfo = "The home baker's cheesecake has an oozy, cheesy centre that is liked by many." + 
                        "A whole cheesecake currently goes for S$47 (U.P.)." + 
                        "It is big enough to serve between seven to eight people.";

    businessContactUrl = "https://paparch.sg/collections/frontpage";
    linkToBusiness = "https://paparch.sg/pages/faqs";

    Business mockBusiness3 = new Business(businessName, 
                                            categoryList,
                                            4, 
                                            businessLat, 
                                            businessLong, 
                                            businessAddr, 
                                            businessLogoUrl, 
                                            businessPhotoUrlList,
                                            businessDescr,
                                            menuUrl,
                                            businessOrderInfo,
                                            businessContactUrl,
                                            linkToBusiness);

    businessName = "Bekal Mama";
    categoryList = Arrays.asList("Mains");
    businessLat = 1.315120F;
    businessLong = 103.764977F;
    businessAddr = "3150 Commonwealth Avenue West, 129580";
    businessLogoUrl = "https://res-1.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_120,w_120,f_auto,b_white,q_auto:eco/v1412921346/nnzir0xhukjpbpmctwy9.jpg";
    businessPhotoUrlList = Arrays.asList("images/the-oven-bakes/the-oven-bakes-1.PNG","images/the-oven-bakes/the-oven-bakes-2.PNG","images/the-oven-bakes/the-oven-bakes-3.PNG","images/the-oven-bakes/the-oven-bakes-5.PNG");

    businessDescr = "Bekal Mama is a food delivery service in Singapore serving Malay food cooked lovingly straight from a mother's heart." +
                    "What makes it special is that it has a weekly menu, which means there is something new to look forward to each week." +
                    "Some of its popular dishes include mutton steak (from S$19).";

    menuUrl = "https://www.instagram.com/bekalmama.sg/";
    businessOrderInfo = "Mutton steak/ Tulang Berempah will be on the menu this weekend. " + 
                        "Succulent mutton steak with bone marrow, a delicacy, is cooked with spices, fresh tomatoes, mint, and onions, " +
                        "and mixed with peas and carrots, and topped with mushrooms. Absolutely delish to eat with French loaves. " +
                        "1-2 pax mutton steak, $19, 3-4 pax mutton steak, $29, 5-6 pax mutton steak, $39. Comes with French loaves. " +
                        "$8 delivery nationwide. To order WhatsApp +6598266648.";

    businessContactUrl = "https://api.whatsapp.com/send?phone=6598266648&text=&source=&data=&app_absent=";
    linkToBusiness = "https://www.instagram.com/bekalmama.sg/";

    Business mockBusiness4 = new Business(businessName, 
                                            categoryList,
                                            4, 
                                            businessLat, 
                                            businessLong, 
                                            businessAddr, 
                                            businessLogoUrl, 
                                            businessPhotoUrlList,
                                            businessDescr,
                                            menuUrl,
                                            businessOrderInfo,
                                            businessContactUrl,
                                            linkToBusiness);

    businessName = "HomeSweetOven";
    categoryList = Arrays.asList("Cakes", "Brownies", "Cookies");
    businessLat = 1.315120F;
    businessLong = 103.764977F;
    businessAddr = "3150 Commonwealth Avenue West, 129580";
    businessLogoUrl = "https://res-1.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_120,w_120,f_auto,b_white,q_auto:eco/v1412921346/nnzir0xhukjpbpmctwy9.jpg";
    businessPhotoUrlList = Arrays.asList("images/the-oven-bakes/the-oven-bakes-1.PNG","images/the-oven-bakes/the-oven-bakes-2.PNG","images/the-oven-bakes/the-oven-bakes-3.PNG","images/the-oven-bakes/the-oven-bakes-5.PNG");

    businessDescr = "Hi guys! This recipe was from @CheNom but I used Vanilla yoghurt instead of Sour cream. I decided to add Speculoos Lotus spread cause, why not! " + 
                    "This recipe is super moist and I personally love this! " +
                    "Recipe (10inch):\n" +
                    "227g salted butter\n" +
                    "400g castor sugar\n" +
                    "4 eggs\n" +
                    "320g vanilla yoghurt\n"+
                    "340g self raising flour (sifted)\n" +
                    "1/4cup full cream milk"+
                    "160g speculoos lotus spread (melted)\n"+
                    "160g dark chocolate compound (melted)\n"+
                    "1 tbsp cocoa powder";

    menuUrl = "https://www.instagram.com/homesweetoven";
    businessOrderInfo = "Minimum Order(for delivery): $15\n\n" +
                                "Islandwide Delivery: $10";
    businessContactUrl = "https://www.instagram.com/homesweetoven";
    linkToBusiness = "https://www.instagram.com/homesweetoven/";

    Business mockBusiness5 = new Business(businessName, 
                                            categoryList,
                                            3, 
                                            businessLat, 
                                            businessLong, 
                                            businessAddr, 
                                            businessLogoUrl, 
                                            businessPhotoUrlList,
                                            businessDescr,
                                            menuUrl,
                                            businessOrderInfo,
                                            businessContactUrl,
                                            linkToBusiness);

    businessName = "Raiysha's Satay Goreng";
    categoryList = Arrays.asList("Dessert", "Brownies", "Cookies");
    businessLat = 1.385110F;
    businessLong = 103.745003F;
    businessAddr = "Chua Chu Kang";
    businessLogoUrl = "https://res-1.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_120,w_120,f_auto,b_white,q_auto:eco/v1412921346/nnzir0xhukjpbpmctwy9.jpg";
    businessPhotoUrlList = Arrays.asList("images/the-oven-bakes/the-oven-bakes-1.PNG","images/the-oven-bakes/the-oven-bakes-2.PNG","images/the-oven-bakes/the-oven-bakes-3.PNG","images/the-oven-bakes/the-oven-bakes-5.PNG");

    businessDescr = "The Oven Bakes is a local home business that makes mouth-watering brownies and cookies." +
                            "These homemade treats are sure going to sweeten your day." +
                            "Baked goodies are limited in stock and only available for pre-order. Grab yours soon!\n\n" +
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
                            "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
                            "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit" +
                            "esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in" +
                            "culpa qui officia deserunt mollit anim id est laborum.";

    menuUrl = "https://www.facebook.com/raiyshasataygoreng/";
    businessOrderInfo = "The satay currently comes in two flavours:\n" +
                        "Beef (S$6/S$10)\n" +
                        "Mutton (S$7/S$11)\n" +
                        "Each serving comes with a portion of ketupat, raw onions, cucumber and some peanut sauce.\n" +
                        "Island-wide delivery is available at a flat fee of S$10.";
    businessContactUrl = "https://www.facebook.com/raiyshasataygoreng/";
    linkToBusiness = "https://www.facebook.com/raiyshasataygoreng/";

    Business mockBusiness6 = new Business(businessName, 
                                            categoryList,
                                            2, 
                                            businessLat, 
                                            businessLong, 
                                            businessAddr, 
                                            businessLogoUrl, 
                                            businessPhotoUrlList,
                                            businessDescr,
                                            menuUrl,
                                            businessOrderInfo,
                                            businessContactUrl,
                                            linkToBusiness);

    businessName = "Jia Ji Mei Shi";
    categoryList = Arrays.asList("Mains");
    businessLat = 1.282110F;
    businessLong = 103.843300F;
    businessAddr = "335 Smith St, #02-166, Singapore 050335";
    businessLogoUrl = "https://res-1.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_120,w_120,f_auto,b_white,q_auto:eco/v1412921346/nnzir0xhukjpbpmctwy9.jpg";
    businessPhotoUrlList = Arrays.asList("images/the-oven-bakes/the-oven-bakes-1.PNG","images/the-oven-bakes/the-oven-bakes-2.PNG","images/the-oven-bakes/the-oven-bakes-3.PNG","images/the-oven-bakes/the-oven-bakes-5.PNG");

    businessDescr = "We are a small hawker stall in chinatown complex – run by 3 sisters currently. Serving daily local breakfast food – that is yummy for lunch and dinner too 🙂" +
                    "YAM CAKE recipe is from my grandmother and we make our own chili and sweet sauce to go with the dishes."+
                    "We serve a wide variety (Chee Cheong Fun, Yam Cake, Soon Kueh, Glutinous Rice, Porrideg, Rice Dumplings…..)"+
                    "Currently delivery is via WHYQ (search: Chinatown complex .. and locate us via our stall name) & GRABFOOD (search: jia ji mei shi)"+
                    "CASH only if you visit us at the stall, otherwise payment via the delivery platform"+
                    "Come look for us at the shop, otherwise please use delivery platform (sadly.. limited location as our delivery partners decides where there will send tooooo ☹)";

    menuUrl = "https://jiak.sg/food/jia-ji-mei-shi-%e4%bd%b3%e8%ae%b0%e7%be%8e%e9%a3%9f/";
    businessOrderInfo = "";
    businessContactUrl = "https://jiak.sg/food/jia-ji-mei-shi-%e4%bd%b3%e8%ae%b0%e7%be%8e%e9%a3%9f/";
    linkToBusiness = "https://jiak.sg/food/jia-ji-mei-shi-%e4%bd%b3%e8%ae%b0%e7%be%8e%e9%a3%9f/";

    Business mockBusiness7 = new Business(businessName, 
                                            categoryList,
                                            1, 
                                            businessLat, 
                                            businessLong, 
                                            businessAddr, 
                                            businessLogoUrl, 
                                            businessPhotoUrlList,
                                            businessDescr,
                                            menuUrl,
                                            businessOrderInfo,
                                            businessContactUrl,
                                            linkToBusiness);

    businessName = "Delisnacks @ Tampines";
    categoryList = Arrays.asList("Mains", "Pastries");
    businessLat = 1.346020F;
    businessLong = 103.944460F;
    businessAddr = "Blk 137 Tampines Street 11 #01-42 Singapore 522137";
    businessLogoUrl = "https://res-1.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_120,w_120,f_auto,b_white,q_auto:eco/v1412921346/nnzir0xhukjpbpmctwy9.jpg";
    businessPhotoUrlList = Arrays.asList("images/the-oven-bakes/the-oven-bakes-1.PNG","images/the-oven-bakes/the-oven-bakes-2.PNG","images/the-oven-bakes/the-oven-bakes-3.PNG","images/the-oven-bakes/the-oven-bakes-5.PNG");

    businessDescr = "Selling affordable & delicious food, there is a wide range of snacks such as you-tiao, butterfly, "+
                    "sesame ball & curry puff, you will definitely be spoilt for choice deciding what to eat "+
                    "Even if you’re vegetarian, fret not because this is a vegetarian stall, you can eat to your heart’s content too "+
                    "One of their best sellers is carrot cake sticks (the carrot cake is cut into small strips first before frying) and goreng pisang (fried banana) ";

    menuUrl = "https://jiak.sg/food/delisnacks-tampines/";
    businessOrderInfo = "Minimum Order(for delivery): $15\n\n" +
                                "Islandwide Delivery: $10";
    businessContactUrl = "https://jiak.sg/food/delisnacks-tampines/";
    linkToBusiness = "https://jiak.sg/food/delisnacks-tampines/";

    Business mockBusiness8 = new Business(businessName, 
                                            categoryList, 
                                            businessLat,
                                            4.6F, 
                                            businessLong, 
                                            businessAddr, 
                                            businessLogoUrl, 
                                            businessPhotoUrlList,
                                            businessDescr,
                                            menuUrl,
                                            businessOrderInfo,
                                            businessContactUrl,
                                            linkToBusiness);

    businessName = "Bite Size by Apizski";
    categoryList = Arrays.asList("Mains");
    businessLat = 1.315120F;
    businessLong = 103.764977F;
    businessAddr = "3150 Commonwealth Avenue West, 129580";
    businessLogoUrl = "https://res-1.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_120,w_120,f_auto,b_white,q_auto:eco/v1412921346/nnzir0xhukjpbpmctwy9.jpg";
    businessPhotoUrlList = Arrays.asList("images/the-oven-bakes/the-oven-bakes-1.PNG","images/the-oven-bakes/the-oven-bakes-2.PNG","images/the-oven-bakes/the-oven-bakes-3.PNG","images/the-oven-bakes/the-oven-bakes-5.PNG");

    businessDescr = "Halal homebase 100% muslim-owned. Delivery islandwide. Self collect available tooPM or WA to place order." +
                    "\nWhatsapp only to 90602961. Thank you.";

    menuUrl = "https://jiak.sg/food/bite-size-by-apizski/";
    businessOrderInfo = "Minimum Order(for delivery): $15\n\n" +
                                "Islandwide Delivery: $10";
    businessContactUrl = "https://jiak.sg/food/bite-size-by-apizski/";
    linkToBusiness = "https://jiak.sg/food/bite-size-by-apizski/";

    Business mockBusiness9 = new Business(businessName, 
                                            categoryList,
                                            0.8F, 
                                            businessLat, 
                                            businessLong, 
                                            businessAddr, 
                                            businessLogoUrl, 
                                            businessPhotoUrlList,
                                            businessDescr,
                                            menuUrl,
                                            businessOrderInfo,
                                            businessContactUrl,
                                            linkToBusiness);

    businessName = "Streets of Bangkok";
    categoryList = Arrays.asList("Mains");
    businessLat = 1.297275F;
    businessLong = 103.78742F;
    businessAddr = "73A Ayer Rajah Crescent, 139957, Singapore";
    businessLogoUrl = "https://res-1.cloudinary.com/crunchbase-production/image/upload/c_lpad,h_120,w_120,f_auto,b_white,q_auto:eco/v1412921346/nnzir0xhukjpbpmctwy9.jpg";
    businessPhotoUrlList = Arrays.asList("images/the-oven-bakes/the-oven-bakes-1.PNG","images/the-oven-bakes/the-oven-bakes-2.PNG","images/the-oven-bakes/the-oven-bakes-3.PNG","images/the-oven-bakes/the-oven-bakes-5.PNG");

    businessDescr = "Serving you authentic dishes found along streets of Bangkok\n\n" +
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
                    "labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
                    "nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit" +
                    "esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in" +
                    "culpa qui officia deserunt mollit anim id est laborum.";

    menuUrl = "https://www.instagram.com/streetsofbangkok.sg/";
    businessOrderInfo = "Minimum Order(for delivery): $15\n\n" +
                                "Islandwide Delivery: $10";
    businessContactUrl = "https://www.facebook.com/streetsofbangkok/";
    linkToBusiness = "https://www.instagram.com/streetsofbangkok.sg/";

    Business mockBusiness10 = new Business(businessName, 
                                            categoryList,
                                            4.9F, 
                                            businessLat, 
                                            businessLong, 
                                            businessAddr, 
                                            businessLogoUrl, 
                                            businessPhotoUrlList,
                                            businessDescr,
                                            menuUrl,
                                            businessOrderInfo,
                                            businessContactUrl,
                                            linkToBusiness);

    mockDataList = Arrays.asList(mockBusiness1, mockBusiness2, mockBusiness3, mockBusiness4, mockBusiness5, mockBusiness6, mockBusiness7, mockBusiness8, mockBusiness9, mockBusiness10);
  }

  /**
  * Access the mock data through this list.
  */
  public List<Business> mockDataList = new ArrayList<Business>();

}


