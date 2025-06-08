import { error } from '@sveltejs/kit';
import type { PageServerLoad } from './$types';

export const load: PageServerLoad = async () => {
	// make a story behind the scenes - Shamu Neko -> mocked from Kuroneko Yamato
	const trackingId = '1234567890';
	// const post = await fetch(`http://localhost:8080/api/deliver/status/${trackingId}`);

	// if (post && post.ok) {
	// 	return post.json();
	// }

	// throw error(404, 'Not found');
	//
	return {
		data: {
			status: 'Delivered'
		}
	};
};
