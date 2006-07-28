/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar2.flex2.rpc.remoting.service.browser.ui {
    
    import flash.events.Event;
    import flash.events.IOErrorEvent;
    import flash.events.NetStatusEvent;
    import flash.events.SecurityErrorEvent;
    import flash.geom.Point;
    import flash.ui.Mouse;
    
    import mx.collections.ArrayCollection;
    import mx.collections.IList;
    import mx.containers.Canvas;
    import mx.controls.Alert;
    import mx.controls.Button;
    import mx.controls.DataGrid;
    import mx.controls.Label;
    import mx.controls.List;
    import mx.controls.ToolTip;
    import mx.controls.dataGridClasses.DataGridColumn;
    import mx.events.DataGridEvent;
    import mx.events.FlexEvent;
    import mx.events.ListEvent;
    import mx.managers.ILayoutManagerClient;
    import mx.managers.ToolTipManager;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    
    import org.seasar.flex2.rpc.remoting.RemoteService;
    import org.seasar2.flex2.rpc.remoting.service.browser.ServiceDetail;
    
    public class BrowserBase extends Canvas{
        public var serviceNamesList:List;
        public var serviceNameLabel:Label;
        public var classNameLabel:Label;
        public var interfacesList:List;
        public var methodsGrid:DataGrid;
        public var refreshBtn:Button;
        
        private var service:RemoteService;
        
        public function BrowserBase(){
            super();
            service = new RemoteService();
        }
        
        protected override function initializationComplete():void{
            super.initializationComplete();
            initEventHandler();
        }
        
        private function initEventHandler():void{
            refreshBtn.addEventListener(FlexEvent.BUTTON_DOWN,refreshClickHandler);
            
            service.destination = "browserService";
            service.addEventListener(IOErrorEvent.IO_ERROR,failurProcess);
            service.addEventListener(IOErrorEvent.NETWORK_ERROR,failurProcess);
            service.addEventListener(NetStatusEvent.NET_STATUS,failurProcess);
            service.addEventListener(SecurityErrorEvent.SECURITY_ERROR,failurProcess);
            service.addEventListener(FaultEvent.FAULT, failurProcess);
            
            serviceNamesList.addEventListener(ListEvent.ITEM_CLICK,serviceNameClickHandler);
        }
        
        private function refreshClickHandler( e:FlexEvent ):void{
            refreshServiceName();
        }

        private function serviceNameClickHandler( e:ListEvent ):void{
            showServiceDetail();
        }
        
        private function refreshServiceName():void{
            var service_:RemoteService = service;
            service_.addEventListener(ResultEvent.RESULT,processRefreshServiceName);
            service_.getServiceNames();
        }
        
        private function processRefreshServiceName( e:ResultEvent ):void{
            service.removeEventListener(ResultEvent.RESULT,processRefreshServiceName);
            var serviceNames:Array = e.result as Array;
            if( serviceNames.length > 0 ){
                serviceNames.sort();
                serviceNamesList.dataProvider = serviceNames;
            }
        }
                
        private function showServiceDetail():void{
            clearDetail();
            var serviceName:String = serviceNamesList.selectedItem as String;
            service.addEventListener(ResultEvent.RESULT,processShowServiceDetail);            
            service.getServiceDetail( serviceName);
        }
        
        private function processShowServiceDetail( e:ResultEvent ):void{
            service.removeEventListener(ResultEvent.RESULT,processShowServiceDetail);
            setupDetail( e.result as ServiceDetail );
        }
        
        private function setupDetail( detail:ServiceDetail ):void{
            serviceNameLabel.text = detail.name;
            classNameLabel.text = detail.className;
            interfacesList.dataProvider = new ArrayCollection( detail.interfaces );
            methodsGrid.dataProvider = new ArrayCollection( detail.methodDetails );
        }
                
        private function failurProcess( e:Event ):void{
            Alert.show( "type:"+e.type  );
        }

        private function clearServiceName():void{
            var listdataProvider:IList = serviceNamesList.dataProvider as IList;
            if( listdataProvider != null ){
                listdataProvider.removeAll();
            }
        }
        
        private function clearDetail():void{
            serviceNameLabel.text = "";
            classNameLabel.text = "";
            var listdataProvider:IList = interfacesList.dataProvider as IList;
            if( listdataProvider != null ){
                listdataProvider.removeAll();
            }
            listdataProvider = methodsGrid.dataProvider as IList;
            if( listdataProvider != null ){
                listdataProvider.removeAll();
            }
        }
        
        private function clearNames():void{
            var listdataProvider:IList = serviceNamesList.dataProvider as IList;
            if( listdataProvider != null ){
                listdataProvider.removeAll();
            }
        }
        
        public function clear():void{
            clearDetail();
            clearNames();
        }
        
        public function browse():void{
            clear();
            refreshServiceName();
        }
        
        public function get gateway():String{
            return service.gatewayUrl;
        }
        
        public function set gateway( gatewayUrl:String ):void{
            service.gatewayUrl = gatewayUrl;
        }
    }
}