import ReactMarkdown from 'react-markdown/with-html';
import anime from 'animejs/lib/anime.es.js';
import { debounce } from 'debounce';
import onClickOutside from 'react-onclickoutside';
import ReactScrollWheelHandler from "react-scroll-wheel-handler";
import hljs from 'highlight.js/lib/highlight';
import clojure from 'highlight.js/lib/languages/clojure';

window.ReactMarkdown = ReactMarkdown;
window.Anime = anime;
window.Debounce = debounce;
window.OnClickOutside = onClickOutside;
window.ReactScrollWheelHandler = ReactScrollWheelHandler;
window.HljsKit = {
    Hljs: hljs,
    CljHljs: clojure
};