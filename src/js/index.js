import ReactMarkdown from 'react-markdown/with-html';
import anime from 'animejs/lib/anime.es.js';
import { debounce } from 'debounce';
import onClickOutside from 'react-onclickoutside';
import ReactScrollWheelHandler from "react-scroll-wheel-handler";

window.ReactMarkdown = ReactMarkdown;
window.anime = anime;
window.debounce = debounce;
window.OnClickOutside = onClickOutside;
window.ReactScrollWheelHandler = ReactScrollWheelHandler;