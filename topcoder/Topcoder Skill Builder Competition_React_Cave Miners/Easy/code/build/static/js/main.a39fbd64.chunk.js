(this["webpackJsonptopcoder-learning-react"]=this["webpackJsonptopcoder-learning-react"]||[]).push([[0],{27:function(e,t,n){},28:function(e,t,n){},35:function(e,t,n){"use strict";n.r(t);var r=n(0),c=n.n(r),o=n(11),i=n.n(o),a=(n(27),n.p+"static/media/logo.6ce24c58.svg"),s=(n(28),n(15)),u=n(3),d=n(13),j=n(14),p=n(18),l=n(16),b=n(9),h=n(1),O=function(e){Object(p.a)(n,e);var t=Object(l.a)(n);function n(){return Object(d.a)(this,n),t.apply(this,arguments)}return Object(j.a)(n,[{key:"render",value:function(){for(var e=this,t=this.props.orenum,n=[],r=0;r<t;++r)n.push("ore"+(r+1));var c=n.map((function(e,t){return Object(h.jsx)("input",{type:"text",value:"abc",id:e})}));return Object(h.jsxs)("div",{children:[Object(h.jsx)("div",{children:c}),Object(h.jsx)("div",{children:Object(h.jsx)("button",{id:"addMoreButton",onClick:function(){e.props.onAddClick(e.props.orenum)},children:"Add More"})}),Object(h.jsx)("div",{children:Object(h.jsx)("button",{id:"doneButton",onClick:function(){e.props.history.push("/overview",{state:t})},children:"done"})})]})}}]),n}(c.a.Component),f=Object(b.b)((function(e){return{orenum:e.orenum}}),(function(e){return{onAddClick:function(t){var n;e((n=t,{type:"ADD_MORE_BUTTON",id:n++}))},onDoneClick:function(t){e(function(e){return{type:"DONE",id:e}}(t))}}}))(O),v=function(e){Object(p.a)(n,e);var t=Object(l.a)(n);function n(){return Object(d.a)(this,n),t.apply(this,arguments)}return Object(j.a)(n,[{key:"render",value:function(){for(var e=this.props.orenum,t=[],n=0;n<e;++n)t.push(n);var r=t.map((function(e,t){return Object(h.jsxs)("li",{children:["ore",t+1]})}));return Object(h.jsxs)("div",{children:["Overview",Object(h.jsx)("ul",{children:r})]})}}]),n}(c.a.Component),x=Object(b.b)((function(e){return{orenum:e.orenum}}),(function(e){}))(v);var m=function(){return Object(h.jsx)("div",{className:"App",children:Object(h.jsxs)("header",{className:"App-header",children:[Object(h.jsx)("img",{src:a,className:"App-logo",alt:"logo"}),Object(h.jsxs)("p",{children:["Edit ",Object(h.jsx)("code",{children:"src/App.js"})," and save to reload."]}),Object(h.jsx)("a",{className:"App-link",href:"https://reactjs.org",target:"_blank",rel:"noopener noreferrer",children:"Learn React"}),Object(h.jsxs)(s.a,{children:[Object(h.jsx)(u.a,{component:f,exact:!0,path:"/"}),Object(h.jsx)(u.a,{component:x,exact:!0,path:"/overview"})]})]})})},g=function(e){e&&e instanceof Function&&n.e(3).then(n.bind(null,36)).then((function(t){var n=t.getCLS,r=t.getFID,c=t.getFCP,o=t.getLCP,i=t.getTTFB;n(e),r(e),c(e),o(e),i(e)}))},k=n(17),y=function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:1,t=arguments.length>1?arguments[1]:void 0;switch(t.type){case"ADD_MORE_BUTTON":return t.id+1;case"DONE":return t.id;default:return e}},A=Object(k.a)({orenum:y}),C=Object(k.b)(A);i.a.render(Object(h.jsx)(c.a.StrictMode,{children:Object(h.jsx)(b.a,{store:C,children:Object(h.jsx)(s.a,{children:Object(h.jsx)(m,{})})})}),document.getElementById("root")),g()}},[[35,1,2]]]);
//# sourceMappingURL=main.a39fbd64.chunk.js.map