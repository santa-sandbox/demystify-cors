<script lang="ts">
	type DeliveryStatus = {
		id: string;
		status: string;
		title: string;
		description: string;
		location: string;
		timestamp: string;
		estimatedDelivery: string;
	};
	let trackingNumber = $state('');
	let deliveryStatus: DeliveryStatus | null = $state(null);
	let hasSearched = $state(false);
	let isLoading = $state(false);

	let year = new Date().getFullYear();

	const API_BASE_URL = 'http://localhost:8080/api/delivery/status';

	async function trackPackage() {
		if (!trackingNumber.trim()) return;

		isLoading = true;
		deliveryStatus = null;
		hasSearched = true;

		try {
			const response = await fetch(`${API_BASE_URL}/${encodeURIComponent(trackingNumber.trim())}`, {
				method: 'GET',
				headers: {
					'Content-Type': 'application/json'
				}
			});

			if (response.ok) {
				const data = await response.json();
				deliveryStatus = {
					id: data.trackingId || trackingNumber,
					status: data.status || 'unknown',
					title: getStatusTitle(data.status),
					description: data.description || getStatusDescription(data.status),
					location: data.location || 'Unknown',
					timestamp: data.lastUpdate || data.timestamp || new Date().toISOString(),
					estimatedDelivery: data.estimatedDelivery || 'N/A'
				};
			} else if (response.status === 404) {
				deliveryStatus = {
					id: trackingNumber,
					status: 'not-found',
					title: 'Package Not Found',
					description: 'No package found with this tracking number',
					location: 'Unknown',
					timestamp: 'N/A',
					estimatedDelivery: 'N/A'
				};
			} else {
				throw new Error(`HTTP ${response.status}: ${response.statusText}`);
			}
		} catch (error) {
			console.error('Error fetching delivery status:', error);
			deliveryStatus = {
				id: trackingNumber,
				status: 'error',
				title: 'Service Unavailable',
				description: 'Unable to fetch delivery status. Please try again later.',
				location: 'Unknown',
				timestamp: 'N/A',
				estimatedDelivery: 'N/A'
			};
		} finally {
			isLoading = false;
		}
	}

	function getStatusTitle(status: string) {
		switch (status?.toLowerCase()) {
			case 'delivered':
				return 'Package Delivered';
			case 'in-transit':
			case 'in_transit':
			case 'out-for-delivery':
				return 'Out for Delivery';
			case 'processing':
			case 'preparing':
				return 'Order Processing';
			case 'shipped':
				return 'Package Shipped';
			case 'pending':
				return 'Order Pending';
			default:
				return 'Status Unknown';
		}
	}

	function getStatusDescription(status: string) {
		switch (status?.toLowerCase()) {
			case 'delivered':
				return 'Your package has been delivered successfully';
			case 'in-transit':
			case 'in_transit':
			case 'out-for-delivery':
				return 'Your package is on the delivery truck';
			case 'processing':
			case 'preparing':
				return 'Your order is being prepared for shipment';
			case 'shipped':
				return 'Your package has been shipped and is on its way';
			case 'pending':
				return 'Your order is confirmed and pending processing';
			default:
				return 'Status information unavailable';
		}
	}

	function getStatusColor(status: string) {
		switch (status?.toLowerCase()) {
			case 'delivered':
				return 'bg-emerald-50 text-emerald-600';
			case 'in-transit':
			case 'in_transit':
			case 'out-for-delivery':
			case 'shipped':
				return 'bg-blue-50 text-blue-600';
			case 'processing':
			case 'preparing':
			case 'pending':
				return 'bg-amber-50 text-amber-600';
			case 'error':
				return 'bg-red-50 text-red-600';
			case 'not-found':
				return 'bg-gray-50 text-gray-600';
			default:
				return 'bg-slate-50 text-slate-600';
		}
	}

	function getStatusIcon(status: string) {
		switch (status?.toLowerCase()) {
			case 'delivered':
				return '✅';
			case 'in-transit':
			case 'in_transit':
			case 'out-for-delivery':
				return '🚚';
			case 'shipped':
				return '📫';
			case 'processing':
			case 'preparing':
				return '📦';
			case 'pending':
				return '⏳';
			case 'error':
				return '⚠️';
			case 'not-found':
				return '🔍';
			default:
				return '❓';
		}
	}
</script>

<svelte:head>
	<title>シャムネコ Delivery - Track Your Package</title>
	<meta name="description" content="Track your delivery with SHAMU NEKO Delivery Service" />
</svelte:head>

<div
	class="rid font-inter h-screen min-h-screen grid-rows-[auto_1fr_auto] overflow-y-auto bg-gradient-to-br from-amber-50 via-orange-50 to-yellow-50"
>
	<!-- Header -->
	<header class="bg-gradient-to-r from-amber-900 via-orange-900 to-yellow-900 text-white shadow-xl">
		<div class="mx-auto max-w-7xl px-4 py-6 sm:px-6 lg:px-8">
			<div class="flex items-center justify-between">
				<div class="flex items-center space-x-4">
					<div class="text-4xl">
						<img src="/assets/shamu-logo.png" alt="SHAMU-NEKO-Logo" class="h-12 w-12" />
					</div>
					<div>
						<h1 class="text-2xl font-bold sm:text-3xl">シャムネコ Delivery</h1>
						<p class="text-sm text-amber-200 sm:text-base">SHAMU NEKO Express Service</p>
					</div>
				</div>
				<div class="hidden items-center space-x-6 text-sm sm:flex">
					<span class="rounded-full bg-amber-800 px-3 py-1">Fast & Reliable</span>
					<span class="rounded-full bg-orange-800 px-3 py-1">24/7 Tracking</span>
				</div>
			</div>
		</div>
	</header>

	<!-- Main Content -->
	<main class="mx-auto max-w-4xl px-4 py-8 sm:px-6 sm:py-12 lg:px-8">
		<!-- Hero Section -->
		<div class="mb-12 text-center">
			<h2 class="mb-4 text-3xl font-bold text-amber-900 sm:text-4xl lg:text-5xl">
				Track Your Package
			</h2>
			<p class="mx-auto max-w-2xl text-lg text-amber-700 sm:text-xl">
				Enter your tracking number to see real-time updates on your delivery status
			</p>
		</div>

		<!-- Tracking Form -->
		<div class="mb-8 rounded-3xl border-2 border-amber-100 bg-white p-6 shadow-2xl sm:p-8">
			<div class="mx-auto max-w-md">
				<label for="tracking" class="mb-2 block text-sm font-medium text-amber-900">
					Tracking Number
				</label>
				<div class="flex gap-3">
					<input
						id="tracking"
						type="text"
						bind:value={trackingNumber}
						placeholder="Enter tracking number (e.g., SHAMU001)"
						class="flex-1 rounded-xl border-2 border-amber-200 px-4 py-3 transition-colors outline-none focus:border-amber-500 focus:ring-2 focus:ring-amber-500"
						onkeydown={(e) => e.key === 'Enter' && trackPackage()}
					/>
					<button
						onclick={trackPackage}
						disabled={!trackingNumber.trim() || isLoading}
						class="transform rounded-xl bg-gradient-to-r from-amber-600 to-orange-600 px-6 py-3 font-medium text-white transition-all duration-200 hover:scale-105 hover:from-amber-700 hover:to-orange-700 focus:ring-2 focus:ring-amber-500 focus:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
					>
						{#if isLoading}
							<div class="flex items-center space-x-2">
								<div
									class="h-4 w-4 animate-spin rounded-full border-2 border-white border-t-transparent"
								></div>
								<span class="hidden sm:inline">Tracking...</span>
							</div>
						{:else}
							<span class="hidden sm:inline">Track Package</span>
							<span class="sm:hidden">🔍</span>
						{/if}
					</button>
				</div>
			</div>
		</div>

		<!-- API Information -->
		<div class="mb-8 text-center">
			<p class="mb-2 text-sm text-amber-600">Enter your tracking number to check delivery status</p>
			<p class="text-xs text-amber-500">API: GET localhost:8080/api/delivery/status/$trackingId</p>
		</div>

		<!-- Zero State -->
		{#if !hasSearched && !deliveryStatus}
			<div class="rounded-3xl border-2 border-amber-100 bg-white p-6 shadow-2xl sm:p-8">
				<div class="text-center">
					<div class="mb-6 text-6xl sm:text-7xl">📦</div>
					<h3 class="mb-4 text-2xl font-bold text-amber-900 sm:text-3xl">
						Ready to Track Your Package?
					</h3>
					<p class="mx-auto max-w-md text-lg text-amber-700">
						Enter your tracking number above to see real-time delivery updates and progress
					</p>
					<div class="mt-8 grid grid-cols-1 gap-4 sm:grid-cols-3">
						<div class="rounded-xl bg-amber-50 p-4">
							<div class="mb-2 text-2xl">🚚</div>
							<p class="text-sm font-medium text-amber-900">Real-time Updates</p>
						</div>
						<div class="rounded-xl bg-amber-50 p-4">
							<div class="mb-2 text-2xl">📍</div>
							<p class="text-sm font-medium text-amber-900">Location Tracking</p>
						</div>
						<div class="rounded-xl bg-amber-50 p-4">
							<div class="mb-2 text-2xl">⏰</div>
							<p class="text-sm font-medium text-amber-900">Delivery Timeline</p>
						</div>
					</div>
				</div>
			</div>
		{/if}

		<!-- Delivery Status -->
		{#if deliveryStatus}
			<div class="rounded-3xl border-2 border-amber-100 bg-white p-6 shadow-2xl sm:p-8">
				<div class="mb-6 text-center">
					<div class="mb-4 text-6xl sm:text-7xl">
						{getStatusIcon(deliveryStatus.status)}
					</div>
					<h3 class="mb-2 text-2xl font-bold text-amber-900 sm:text-3xl">
						{deliveryStatus.title}
					</h3>
					<p class="text-lg text-amber-700">
						Tracking: <span class="font-mono font-bold">{deliveryStatus.id}</span>
					</p>
				</div>

				<div class="mb-8 grid grid-cols-1 gap-6 md:grid-cols-2">
					<!-- Status Badge -->
					<div class="rounded-2xl bg-amber-50 p-6">
						<h4 class="mb-3 font-semibold text-amber-900">Current Status</h4>
						<div class="flex items-center space-x-3">
							<span
								class={`rounded-full px-4 py-2 text-sm font-medium ${getStatusColor(deliveryStatus.status)}`}
							>
								{deliveryStatus.status.replace('-', ' ').toUpperCase()}
							</span>
						</div>
						<p class="mt-3 text-amber-700">{deliveryStatus.description}</p>
					</div>

					<!-- Location & Time -->
					<div class="rounded-2xl bg-amber-50 p-6">
						<h4 class="mb-3 font-semibold text-amber-900">Details</h4>
						<div class="space-y-3">
							<div class="flex items-start space-x-3">
								<span class="text-amber-600">📍</span>
								<div>
									<p class="font-medium text-amber-900">Location</p>
									<p class="text-sm text-amber-700">{deliveryStatus.location}</p>
								</div>
							</div>
							<div class="flex items-start space-x-3">
								<span class="text-amber-600">🕐</span>
								<div>
									<p class="font-medium text-amber-900">Last Update</p>
									<p class="text-sm text-amber-700">{deliveryStatus.timestamp}</p>
								</div>
							</div>
							<div class="flex items-start space-x-3">
								<span class="text-amber-600">📅</span>
								<div>
									<p class="font-medium text-amber-900">Estimated Delivery</p>
									<p class="text-sm text-amber-700">{deliveryStatus.estimatedDelivery}</p>
								</div>
							</div>
						</div>
					</div>
				</div>

				<!-- Progress Timeline -->
				{#if deliveryStatus.status !== 'not-found' && deliveryStatus.status !== 'error'}
					<div class="rounded-2xl bg-gradient-to-r from-amber-50 to-orange-50 p-6">
						<h4 class="mb-4 font-semibold text-amber-900">Delivery Progress</h4>
						<div class="relative">
							<div class="absolute top-0 bottom-0 left-4 w-0.5 bg-amber-200"></div>

							<!-- Order Placed -->
							<div class="mb-6 flex items-center space-x-4">
								<div
									class="relative z-10 flex h-8 w-8 items-center justify-center rounded-full bg-emerald-500 text-sm font-bold text-white"
								>
									✓
								</div>
								<div>
									<p class="font-medium text-amber-900">Order Placed</p>
									<p class="text-sm text-amber-600">Your order has been confirmed</p>
								</div>
							</div>

							<!-- Processing -->
							<div class="mb-6 flex items-center space-x-4">
								<div
									class={`relative z-10 flex h-8 w-8 items-center justify-center rounded-full text-sm font-bold ${
										[
											'processing',
											'preparing',
											'in-transit',
											'in_transit',
											'shipped',
											'out-for-delivery',
											'delivered'
										].includes(deliveryStatus.status?.toLowerCase())
											? 'bg-emerald-500 text-white'
											: 'bg-amber-200 text-amber-600'
									}`}
								>
									{[
										'processing',
										'preparing',
										'in-transit',
										'in_transit',
										'shipped',
										'out-for-delivery',
										'delivered'
									].includes(deliveryStatus.status?.toLowerCase())
										? '✓'
										: '2'}
								</div>
								<div>
									<p class="font-medium text-amber-900">Processing</p>
									<p class="text-sm text-amber-600">Package is being prepared</p>
								</div>
							</div>

							<!-- In Transit -->
							<div class="mb-6 flex items-center space-x-4">
								<div
									class={`relative z-10 flex h-8 w-8 items-center justify-center rounded-full text-sm font-bold ${
										[
											'in-transit',
											'in_transit',
											'shipped',
											'out-for-delivery',
											'delivered'
										].includes(deliveryStatus.status?.toLowerCase())
											? 'bg-emerald-500 text-white'
											: 'bg-amber-200 text-amber-600'
									}`}
								>
									{[
										'in-transit',
										'in_transit',
										'shipped',
										'out-for-delivery',
										'delivered'
									].includes(deliveryStatus.status?.toLowerCase())
										? '✓'
										: '3'}
								</div>
								<div>
									<p class="font-medium text-amber-900">In Transit</p>
									<p class="text-sm text-amber-600">Package is on the way</p>
								</div>
							</div>

							<!-- Delivered -->
							<div class="flex items-center space-x-4">
								<div
									class={`relative z-10 flex h-8 w-8 items-center justify-center rounded-full text-sm font-bold ${
										deliveryStatus.status?.toLowerCase() === 'delivered'
											? 'bg-emerald-500 text-white'
											: 'bg-amber-200 text-amber-600'
									}`}
								>
									{deliveryStatus.status?.toLowerCase() === 'delivered' ? '✓' : '4'}
								</div>
								<div>
									<p class="font-medium text-amber-900">Delivered</p>
									<p class="text-sm text-amber-600">Package has been delivered</p>
								</div>
							</div>
						</div>
					</div>
				{/if}
			</div>
		{/if}
	</main>

	<!-- Footer -->
	<footer class="mt-16 bg-gradient-to-r from-amber-900 via-orange-900 to-yellow-900 text-white">
		<div class="mx-auto max-w-7xl px-4 py-8 sm:px-6 lg:px-8">
			<div class="grid grid-cols-1 gap-8 md:grid-cols-3">
				<div>
					<div class="mb-4 flex items-center space-x-2">
						<span class="text-2xl">
							<img src="/assets/shamu-logo.png" alt="Shamuneko Logo" class="h-8 w-8" />
						</span>
						<span class="text-lg font-bold">シャムネコ Delivery</span>
					</div>
					<p class="text-amber-200">
						Fast, reliable delivery service with the grace of a SHAMU NEKO.
					</p>
				</div>
				<div>
					<h4 class="mb-4 font-semibold">Quick Links</h4>
					<ul class="space-y-2 text-amber-200">
						<li><a href="/track" class="transition-colors hover:text-white">Track Package</a></li>
						<li><a href="/rates" class="transition-colors hover:text-white">Shipping Rates</a></li>
						<li><a href="/support" class="transition-colors hover:text-white">Support</a></li>
					</ul>
				</div>
				<div>
					<h4 class="mb-4 font-semibold">Contact</h4>
					<div class="space-y-2 text-amber-200">
						<p>📞 1-800-486</p>
						<p>📧 support@shamuneko-delivery.com</p>
						<p>🌐 www.shamuneko-delivery.com</p>
					</div>
				</div>
			</div>
			<div class="mt-8 border-t border-amber-800 pt-8 text-center text-amber-200">
				<p>&copy; {year} シャムネコ Delivery. All rights reserved.</p>
			</div>
		</div>
	</footer>
</div>
