
/**
 * @author sefialbo 
 * Created: 18.6.17
 * 
 * This file contains the services for the path navigation of the user
 */

import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import {MyApp} from '../app/app.component';
import 'rxjs/add/operator/map';
import 'rxjs/Rx';

@Injectable()
export class PathService {
     constructor(public http: Http) {
        console.log('Hello PathService Provider');
    }
    getRecordedPath(parkingArea:string ,destination:string ,retData:any,googleObj,callback){
        return this.http.get('https://spring-boot-nav.herokuapp.com/GetPath?area='+parkingArea+"&dest="+destination).map(res => res.json()).subscribe(data => {
            if(data.error){
                retData.error="there is an error";
                
            }else{
                retData.duration=data.Duration;
                retData.path=[];
                data.Route.forEach(function(element){
                    retData.path.push({lat:element.lat,lng: element.lon});
                });
                retData.description=data.Description;
            }
            callback();
        });
       
    }
    
        getLastPaths(callback:any) {
        return this.http.get('https://spring-boot-nav.herokuapp.com/GetLastPaths/'+MyApp.id).map(res => res.json()).subscribe(data => {
            callback(data)//callback(data.SavedPaths)
        }); 
    }
    sendRecordedPath(toSend){
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');

        return new Promise(resolve => {
            this.http.post('https://spring-boot-nav.herokuapp.com/SendPath', toSend, { headers: headers }).subscribe(data => {
                if (data.status == 200) {
                    resolve(true);
                }
                else
                    resolve(false);
            });
        });
    }

}