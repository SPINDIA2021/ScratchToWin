

var gradle = gradle || {
    debug : true,
	isMobile : ( /(ipad|iphone|ipod|android|windows phone)/i.test(navigator.userAgent) ),

	//Ads information
	//===============
    banner             : 'ca-app-pub-8934341497806743/9154279526', //id placement banner
    interstitial       : 'ca-app-pub-8934341497806743/7445612034', //id placement interstitial

    isTesting          : false, //Ads mode testing. set to false for a production mode.
    enableBanner       : true, //Ads enable the banner. set to false to disable the banner.
    enableInterstitial : true, //Ads enable the interstitial. set to false to disable all interstitials.

    bannerAtBottom     : true, //if false the banner will be at top
    overlap            : false,

	notifiBackbutton   : true, //for confirmation backbutton
	notifiMessage      : 'Do you want to exit the game ?',

	intervalAds        : 1,     //Ads each interval for example each n times
	
	fullsize		   : true,

	// more games
	//===========
						//change the value with your id developer :
	developer_link    : 'https://play.google.com/store/apps/developer?id=vishusarkar',



	//Events manager :
	//================
    event: function(ev, msg){ gradle.process(ev,msg);switch(ev){
		
		case 'first_start':
			//gradle.showInter();
			break;
		case 'show_splash': //Button play (on start screen)
			//gradle.showInter();
			break;
		case 'button_play':  //button play 
			gradle.showInter();
			break;
		case 'win':  //you win screen
			gradle.showInter();
			break;
		case 'showAds':  //game over
			gradle.showInter();
			break;
		case 'pause':
			gradle.showInter();
		case 'resume':
			//gradle.showInter();
			break;
		case 'more_games':
			gradle.more();
			break;
		case 'test':
			//gradle.checkInterval() && gradle.showInter();
			break;		
			
    }},
	

    log: function(val){
        if(typeof val === 'object' && typeof val.isTrusted!='undefined' && val.isTrusted==false) return;
        gradle.debug && console.log( gradle.isMobile && (typeof val === 'object' && val !== null) ? JSON.stringify(val) : val );
    },
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Ready : /!\ DO NOT CHANGE, ONLY IF YOU ARE AN EXPERT.
	//=========================
	first_start: true,
    start: function(){
		//initApp();
	},
	processBackbutton : function(){ //return null;
		var key=null;
		/*if(typeof game.state.getCurrentState().key!='undefined'){
			key = game.state.getCurrentState().key;
			switch(key){
				case 'MainMenuState':
					key=null;
					break;
				case 'SelectPictureState':
					game.state.start('MainMenuState');
					break;
				case 'GameState':
					game.state.start('MainMenuState');
					break;
			}
		}*/
		return key;
	},
	process: function(ev, msg){
		gradle.log(ev);
		if(gradle.first_start && ev=='SCREEN_HOME'){gradle.hideSplash();return !1;}
		/*switch(ev){
			case 'main_menu':
				//document.body.style.backgroundImage = "url('images/bg_menu.jpg')";
				break;
			case 'list_picture':
				//document.body.style.backgroundImage = "url('images/bg_select.jpg')";
				break;
			case 'draw_image':
				//document.body.style.backgroundImage = "url('images/bg_game.jpg')";
				break;
		}*/
		if(gradle.isMobile && typeof cordova!='undefined'){
			//cordova.plugins.firebase.analytics.logEvent("event", {param1: ev});
		}
		return !0;
	},
	onVisibilityNo : function(){
        gradle.log('visibility no');
        //gradle_onPauseRequested();
    },
	onVisibilityYes : function(){
		gradle.log('visibility yes');
        //gradle_onResumeRequested();
    },
	
	
	
	
	
	
	
	
	
	
	
	//Ready : /!\ DO NOT CHANGE, ONLY IF YOU ARE AN EXPERT.
	//=========================
	ready: function() {
        gradle.event('gradle ready ...');
        if(typeof admob !='undefined'){
            if(gradle.isTesting){
                admob.banner.config({
                    id: gradle.banner,
                    isTesting: true,
                    autoShow: true,
                    overlap: gradle.overlap,
                    offsetTopBar: false,
                    bannerAtTop: !gradle.bannerAtBottom
                });

                admob.interstitial.config({
                    id: gradle.interstitial,
                    isTesting: true,
                    autoShow: false
                });
				
				admob.rewardvideo.config({
                    id: gradle.rewardvideo,
                    isTesting: true
                });
            }
            else{
                admob.banner.config({
                    id: gradle.banner,
                    autoShow: true,
                    overlap: gradle.overlap,
                    offsetTopBar: false,
                    bannerAtTop: !gradle.bannerAtBottom
                });

                admob.interstitial.config({
                    id: gradle.interstitial,
                    autoShow: false,
                });
				
				admob.rewardvideo.config({
                    id: gradle.rewardvideo
                });
            }
        }
        if(gradle.enableBanner && typeof admob!=='undefined'){
            admob.banner.prepare();
        }
        gradle.prepareInter();
         document.addEventListener('admob.banner.events.LOAD_FAIL', function(event) {
           gradle.log(event);
        });

        document.addEventListener('admob.banner.events.LOAD', function(event) {
           gradle.log(event);
        });

        document.addEventListener('admob.interstitial.events.LOAD_FAIL', function(event) {
           gradle.log(event);
        });

        document.addEventListener('admob.interstitial.events.LOAD', function(event) {
           gradle.log(event);
        });

        
		document.addEventListener('admob.interstitial.events.OPEN', function(event) {	
            gradle.log(event);
        });
		
		document.addEventListener('admob.interstitial.events.CLOSE', function(event) {
           gradle.log(event);
           admob.interstitial.prepare();
        });
		/*
		document.addEventListener('admob.rewardvideo.events.CLOSE', function(event) {
           gradle.log(event);
           gradle.prepareVideo();
        });
		
		document.addEventListener('admob.rewardvideo.events.REWARD', function(event) {
           gradle.log(event);
		   gradle.videoCallback.success();
        });
		*/

		/*
		All Events :
		============
		admob.banner.events.LOAD
		admob.banner.events.LOAD_FAIL
		admob.banner.events.OPEN
		admob.banner.events.CLOSE
		admob.banner.events.EXIT_APP
		
		admob.banner.prepare()
		admob.banner.show()
		admob.banner.hide()
		admob.banner.remove()
		
		admob.interstitial.events.LOAD
		admob.interstitial.events.LOAD_FAIL
		admob.interstitial.events.OPEN
		admob.interstitial.events.CLOSE
		admob.interstitial.events.EXIT_APP
		
		admob.interstitial.prepare()
		admob.interstitial.show()
		
		admob.rewardvideo.events.LOAD
		admob.rewardvideo.events.LOAD_FAIL
		admob.rewardvideo.events.OPEN
		admob.rewardvideo.events.CLOSE
		admob.rewardvideo.events.EXIT_APP
		admob.rewardvideo.events.START
		admob.rewardvideo.events.REWARD
		
		admob.rewardvideo.prepare()
		admob.rewardvideo.show()
		*/

		gradle.addEventBackbutton();
        /*document.addEventListener("visibilitychange", gradle.onVisibilityChanged, false);
		document.addEventListener("mozvisibilitychange", gradle.onVisibilityChanged, false);
		document.addEventListener("webkitvisibilitychange", gradle.onVisibilityChanged, false);
		document.addEventListener("msvisibilitychange", gradle.onVisibilityChanged, false);*/
		document.addEventListener("pause", gradle.onVisibilityNo, false);
		document.addEventListener("resume", gradle.onVisibilityYes, false);
		gradle.event('first_start');
		gradle.start();
		//document.body.addEventListener('load', function () {
		//}, false);

    },

	onVisibilityChanged : function(){
        try{
            if (document.hidden || document.mozHidden || document.webkitHidden || document.msHidden){
                gradle.onVisibilityNo();
            }else{
                gradle.onVisibilityYes();
            }
        }catch(error){}
    },
    enableMoreGames   : true,
    more: function(){
        (gradle.developer_link!=="")&&window.open(gradle.developer_link);
    },
	
	hideSplash: function(){
        if(gradle.isMobile && typeof cordova!='undefined'){
            cordova.exec(null, null, "SplashScreen", "hide", []);
        }
		gradle.first_start=false;
    },

    prepareInter: function(){
        if(!gradle.isMobile || typeof admob=='undefined' || admob==null) return;
        admob.interstitial.prepare();
    },

    showInter: function(){
        if(!gradle.isMobile || typeof admob=='undefined' || admob==null) return;
        admob.interstitial.show();
    },

	enableVideo        : false, //Ads enable the reward video.
	prepareVideo: function(){
        if(!gradle.isMobile || typeof admob=='undefined' || admob==null) return;
        admob.rewardvideo.prepare();
    },

	videoCallback: { success: null, canceled: null},
    showVideo: function(success, canceled){
		gradle.videoCallback.success  = success;
		gradle.videoCallback.canceled = canceled;
		if(!gradle.enableVideo) return;
        if(!gradle.isMobile || typeof admob=='undefined' || admob==null) return;
		admob.rewardvideo.show();
    },
	
	addEventBackbutton : function(){
		if(gradle.notifiBackbutton){
			document.addEventListener("backbutton", function() {
				if(gradle.processBackbutton()==null){
					navigator.notification.confirm(gradle.notifiMessage, function(buttonIndex){
						if(buttonIndex == 1) {
	                        navigator.app.exitApp();
	                        //cordova.plugins.exit();
	                        return true;
	                    }
	                    else {
	                        return false;
	                    }
					});
				}
			}, !1);
		}
	},

    run : function(){
		gradle.isMobile ? document.addEventListener('deviceready', gradle.ready, false) :  gradle.ready();
    },
	
	trackStats: function(a, b){
		gradle.event(a, b);
	},
	
	trackScreen: function(a,b){
		gradle.event(a,b);
	},
	
	trackEvent: function(a,b){
		gradle.event(a,b);
	},
	
	showAd: function(){
		gradle.event('showAd');
	},
	
	__: function(t){
		return null;//t;
	},
	
	currentInterval : 0,
	unlock_all_levels : false,
    checkInterval: function(){
		return (++gradle.currentInterval==gradle.intervalAds) ? !(gradle.currentInterval=0) : !1;
	},
	
	saveImage: function(base64Data){
		var imageData = base64Data.replace(/data:image\/png;base64,/,'');
		cordova.exec(
			function(msg){
				navigator.notification.alert(gradle.saveImageSuccess, function(buttonIndex){
                    if(buttonIndex == 1) {
                        navigator.app.exitApp();
                        return true;
                    }
                    else {
                        return false;
                    }
                },gradle.dialogTitleSaveImg);
                console.log(msg);
            },
            function(err){
                navigator.notification.alert(gradle.saveImageFailed, function(buttonIndex){
                    if(buttonIndex == 1) {
                        navigator.app.exitApp();
                        return true;
                    }
                    else {
                        return false;
                    }
                },gradle.dialogTitleSaveImg);
                console.log(err);
            },
            "Base64SaveImage","saveImageDataToLibrary",
            [imageData]
        );
	},
	
	buildKey : function(key){
        return "gd.4024."+key;
    },
	
	getStorage: function(key, default_value){
        var value;
        try {
            value = localStorage.getItem(gradle.buildKey(key));
        }
        catch(error){
			return default_value;
        }
		if(value !== undefined && value !=null){
            value = window.atob(value);
        }
		else{
			value = default_value;
		}
        return value;
    },

    setStorage: function(key, value){
        var v = value;
        if(v !== undefined){
            v = window.btoa(v);
        }
        try{
            localStorage.setItem(gradle.buildKey(key), v);
            return value;
        }
        catch(error){
            return undefined;
        }
    }
};

gradle.run();





